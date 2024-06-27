package org.rhythm.service.impl;

import okhttp3.*;
import org.json.JSONObject;
import org.rhythm.entity.Question;
import org.rhythm.properties.WenXinProperties;
import org.rhythm.service.AIService;
import org.rhythm.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class AIServiceImpl implements AIService {
    @Autowired
    private WenXinProperties wenXinProperties;

    public String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType,
                "grant_type=client_credentials&client_id=" + wenXinProperties.getApiKey() +
                "&client_secret=" + wenXinProperties.getSecretKey());
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        OkHttpClient HTTP_CLIENT = new OkHttpClient();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            return new JSONObject(responseBody).getString("access_token");
        }
    }

    @Override
    public QuestionVO answer(Question question){
        System.out.println("question = "+question);
        QuestionVO questionVO = new QuestionVO("AI错误，请联系管理员");
        try {
            String access_token = getAccessToken();
            String baseUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=" + access_token;

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            HashMap<String, String> msg = new HashMap<>();
            msg.put("role", "user");
            msg.put("content", question.getQuestion());

            HashMap<String, Object> requestBody = new HashMap<>();
            requestBody.put("messages", new ArrayList<HashMap>() {{ add(msg); }});

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(mediaType, new JSONObject(requestBody).toString());

            Request request = new Request.Builder()
                    .url(baseUrl)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            OkHttpClient HTTP_CLIENT = new OkHttpClient();
            try (Response response = HTTP_CLIENT.newCall(request).execute()) {
                System.out.println("response = "+response);
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                JSONObject jsonResponse = new JSONObject(response.body().string());
                questionVO.setAnswer(jsonResponse.getString("result"));
                return questionVO;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionVO;
    }
}
