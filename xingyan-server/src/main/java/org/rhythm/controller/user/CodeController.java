package org.rhythm.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.dto.CodeDTO;
import org.rhythm.dto.UserLoginDTO;
import org.rhythm.result.Result;
import org.rhythm.service.CppService;
import org.rhythm.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user/code")
@Slf4j
@Tag(name = "代码请求接口", description = "代码的运行/测试")
public class CodeController {
    @Autowired
    private CppService cppService;

    /**
     * 代码提交
     *
     * @param codeDTO
     * @return
     */
    @Operation(summary = "用户代码提交")
    @PostMapping("/cpp/submit")
    public Result<String> submit(@RequestBody CodeDTO codeDTO) {
        log.info("用户代码提交：{}", codeDTO.getCode());
        String resultStr = cppService.submit(codeDTO.getCode());
        return Result.success(resultStr);
    }
    /**
     * 代码测试运行
     *
     * @param codeDTO
     * @return
     */
    @Operation(summary = "用户代码测试运行")
    @PostMapping("/cpp/testRun")
    public Result<String> testRun(@RequestBody CodeDTO codeDTO) {
        log.info("用户代码提交：{}", codeDTO.getCode());
        String resultStr = cppService.testRun(codeDTO.getCode());
        System.out.println("result = " + resultStr);
        return Result.success(resultStr);
    }
}
