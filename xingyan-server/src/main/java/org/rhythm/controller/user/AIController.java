package org.rhythm.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.dto.QuestionDTO;
import org.rhythm.entity.Question;
import org.rhythm.result.Result;
import org.rhythm.service.AIService;
import org.rhythm.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//AI询问接口
@CrossOrigin
@RequestMapping("/user/ai")
@Tag(name = "AI接口", description = "文心一言AI")
@RestController
@Slf4j
public class AIController {
    @Autowired
    private AIService aiService;

    /**
     * 向AI模型提问
     * @param questionDTO
     * @return
     */
    @Operation(summary = "询问AI")
    @PostMapping("/ask")
    public Result<QuestionVO> ask(@RequestBody QuestionDTO questionDTO){
        Question question = new Question(questionDTO.getQuestion());
        QuestionVO ans = aiService.answer(question);
        return Result.success(ans);
    }

    /**
     * 向AI模型提问代码
     * @param questionDTO
     * @return
     */
    @Operation(summary = "询问AI代码")
    @PostMapping("/ask/code")
    public Result<QuestionVO> askCode(@RequestBody QuestionDTO questionDTO){
        Question question = new Question("下面是我的cpp代码，请你帮我逐行解析一下程序的含义，如果有错误，分析其错误: " + questionDTO.getQuestion());
        QuestionVO ans = aiService.answer(question);
        return Result.success(ans);
    }
}
