package org.rhythm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import okhttp3.*;
import org.rhythm.listener.XinghuoListenr;
import org.rhythm.properties.XunfeiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class AIUtil extends WebSocketListener {
    @Autowired
    private XunfeiProperties xunfeiProperties;

    private String PDFInstructionSupplement = "，支持前3级markdown标题、加粗的markdown语法，" +
            "公式部分请按照$...$的latex公式格式给出，注意不要在标题中出现任何的公式";

    //返回pdf地址
    public String generatePDF(String instruct){
        // 构建鉴权url
        String authUrl = null;
        try {
            authUrl = getAuthUrl(xunfeiProperties.getHostUrl(), xunfeiProperties.getApiKey(),
                    xunfeiProperties.getApiSecret());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        OkHttpClient client = new OkHttpClient.Builder().build();
        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();

        String filepath = generateFileNameByTime("user_pdfs/", ".pdf");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            WebSocket webSocket = null;
            System.out.println("First asynchronous task is running.");
            // 模拟任务执行
            try {
                for (int i = 0; i < 1; i++) {
                    webSocket = client.newWebSocket(request, new XinghuoListenr(i + "",
                            false, xunfeiProperties,
                            instruct + PDFInstructionSupplement, filepath));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }).thenRun(() -> {
            System.out.println("First task has completed, running the next task.");
        });

        future.join(); // 等待future完成
        return filepath;
    }

    public static String generateFileNameByTime(String prefix, String suffix) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedTime = now.format(formatter);
        return prefix + formattedTime + suffix;
    }

    // 鉴权方法
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }
}
