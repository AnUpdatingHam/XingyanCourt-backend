package org.rhythm.service;

import java.io.InputStream;
import java.util.Map;

public interface CppService {
    Map<Integer, String> compile();
    Map<Integer, String> executeCppFile();
    String readOutputFile(InputStream inputStream);

    String submit(String code);

    String testRun(String code);

    void deleteLocalFile();
    void writeTempCppFile(String code);
}
