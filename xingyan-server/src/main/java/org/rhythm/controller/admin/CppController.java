package org.rhythm.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.result.Result;
import org.rhythm.service.AIService;
import org.rhythm.service.CppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.Enumeration;
import java.util.Map;

@CrossOrigin
@RequestMapping("/admin/cpp")
@Tag(name = "cpp接口", description = "编译、运行等")
@RestController
@Slf4j
public class CppController {

    @Autowired
    private CppService cppService;

    @Operation(summary = "编译")
    @GetMapping("/compile")
    public Result<Map<Integer, String>> compileCppFile() {
        // 或者使用 HttpServletRequest 对象获取请求头
        Map<Integer, String> resultMap = cppService.compile();
        return Result.success(resultMap);
    }

    @Operation(summary = "运行")
    @GetMapping("/execute")
    public Result<Map<Integer, String>> executeCppFile() {
        // 或者使用 HttpServletRequest 对象获取请求头
        Map<Integer, String> resultMap = cppService.executeCppFile();
        return Result.success(resultMap);
    }
}
