package org.rhythm.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.dto.TestPaperCreateDTO;
import org.rhythm.result.Result;
import org.rhythm.service.AIService;
import org.rhythm.vo.PDFCreateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//AI询问接口
@CrossOrigin
@RequestMapping("/user/AI")
@Tag(name = "AI接口", description = "讯飞星火AI")
@RestController
@Slf4j
public class AIController {
    @Autowired
    private AIService aiService;

    /**
     * 向AI模型提问
     * @param testPaperCreateDTO
     * @return
     */
    @Operation(summary = "创建PDF")
    @PostMapping("/PDF/create")
    public Result<PDFCreateVO> ask(@RequestBody TestPaperCreateDTO testPaperCreateDTO){
        log.info("准备创建pdf试卷，指令为:{}", testPaperCreateDTO);
        PDFCreateVO ans = aiService.createPDF(testPaperCreateDTO);
        return Result.success(ans);
    }

}
