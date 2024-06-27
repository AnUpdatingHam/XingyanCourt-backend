package org.rhythm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.rhythm.service.CppService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CppServiceImpl implements CppService {
    @Override
    public Map<Integer, String> compile() {
        int k = 0; //无错
        Map<Integer, String> map = new HashMap<>();
        StringBuilder result = new StringBuilder();
        try {
            // 编译命令
            String command = "cl /EHsc temp.cpp";

            // 创建 ProcessBuilder 对象
            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.redirectErrorStream(true);

            // 执行命令
            Process process = pb.start();

            // 获取命令输出
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                System.out.println(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            result.append("\"\nExit Code: \"" + exitCode);
        } catch (IOException | InterruptedException e) {
            result.append("Compile Error");
            k = 1;
            e.printStackTrace();
        }
        System.out.println("result = " + result);
        map.put(k, result.toString());
        return map;
    }

    @Override
    public Map<Integer, String> executeCppFile() {
        int k = 0; //无错
        Map<Integer, String> map = new HashMap<>();
        StringBuilder result = new StringBuilder();
        try {
            // 执行命令
            String command = "temp.exe";
            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // 等待命令执行完成
            int exitCode = process.waitFor();
            log.info("Exit Code: " + exitCode);

            // 在进程执行完毕后读取输出文件内容
            String outputFilePath = "out";
            try (InputStream inputStream = new FileInputStream(outputFilePath)) {
                if (inputStream != null) {
                    result.append(readOutputFile(inputStream));
                } else {
                    result.append("Output file not found.");
                    k = 1;
                }
            }

        } catch (IOException | InterruptedException e) {
            k = 1;
            result.append("Compile Error");
            e.printStackTrace();
        }
        log.info("result = " + result.toString());
        map.put(k, result.toString());
        return map;
    }

    @Override
    public String readOutputFile(InputStream inputStream) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            System.out.println("Output: " + content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public String submit(String code) {
        deleteLocalFile();
        writeTempCppFile(code);
        //String ret = "compile message \n: " + compile();
        //ret += "\n\nexecute message : \n" + executeCppFile();
        Map<Integer, String> compileMap = compile();
        if(compileMap.containsKey(1)){
            log.info("compile error");
            return compileMap.get(1);
        }
        Map<Integer, String> execureMap = executeCppFile();
        if(execureMap.containsKey(1)){
            log.info("runtime error");
            return execureMap.get(1);
        }
        return execureMap.get(0);
    }

    @Override
    public String testRun(String code) {
        deleteLocalFile();
        writeTempCppFile(code);
        Map<Integer, String> compileMap = compile();
        if(compileMap.containsKey(1)){
            log.info("compile error");
            return compileMap.get(1);
        }
        Map<Integer, String> execureMap = executeCppFile();
        if(execureMap.containsKey(1)){
            log.info("runtime error");
            return execureMap.get(1);
        }
        return execureMap.get(0);
    }

    @Override
    public void deleteLocalFile() {
        // 相对目录下的文件路径
        String[] relativeFilePaths = {"temp.cpp",
                "temp.o", "temp.exe", "temp.obj", "out"};

        for (String relativeFilePath : relativeFilePaths) {
            // 构造 File 对象
            File fileToDelete = new File(relativeFilePath);
            // 检查文件是否存在并且是一个文件
            if (fileToDelete.exists() && fileToDelete.isFile()) {
                // 尝试删除文件
                log.info("尝试删除:{}", relativeFilePath);
                boolean deleted = fileToDelete.delete();
                if (deleted) {
                    System.out.println("文件删除成功");
                } else {
                    System.out.println("文件删除失败");
                }
            }
        }
    }

    @Override
    public void writeTempCppFile(String code) {
        // 指定文件路径
        String filePath = "temp.cpp";

        try {
            // 创建 FileWriter 对象
            FileWriter fileWriter = new FileWriter(filePath);

            // 创建 BufferedWriter 对象，用于缓冲写入
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // 将字符串写入文件
            bufferedWriter.write(code);

            // 关闭 BufferedWriter
            bufferedWriter.close();

            log.info("成功写入到文件：{}", filePath);
        } catch (IOException e) {
            log.info("写入文件时发生错误：{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
