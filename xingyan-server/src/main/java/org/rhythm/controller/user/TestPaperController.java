package org.rhythm.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.dto.TestPaperDTO;
import org.rhythm.dto.TestPaperPageQueryDTO;
import org.rhythm.entity.ArticleCatalogue;
import org.rhythm.entity.ArticleCategory;
import org.rhythm.entity.ArticleExtension;
import org.rhythm.entity.TestPaper;
import org.rhythm.result.PageResult;
import org.rhythm.result.Result;
import org.rhythm.service.TestPaperService;
import org.rhythm.vo.ArticleExtensionVO;
import org.rhythm.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//试卷接口
@CrossOrigin
@RequestMapping("/user/testPaper")
@Tag(name = "试卷接口", description = "试卷的新增、查询、删除")
@RestController
@Slf4j
public class TestPaperController {
    @Autowired
    private TestPaperService testPaperService;

    /**
     *新增试卷
     * @param testPaper
     * @return
     */
    @Operation(summary = "新增试卷")
    @PostMapping
    public Result add(@RequestBody TestPaper testPaper){
        log.info("新增试卷: {}", testPaper);
        testPaperService.add(testPaper);
        return Result.success();
    }

    /**
     * 分页查询试卷
     * @param testPaperPageQueryDTO
     * @return
     */
    @Operation(summary = "分页查询试卷")
    @GetMapping("/page")
    public Result<PageResult> page(TestPaperPageQueryDTO testPaperPageQueryDTO){
        log.info("分页查询试卷，参数为：{}", testPaperPageQueryDTO);
        PageResult pageResult = testPaperService.pageQuery(testPaperPageQueryDTO);
        return Result.success(pageResult);
    }
    
    /**
     * 根据id查询试卷
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询试卷")
    @GetMapping("/{id}")
    public Result<TestPaper> getById(@PathVariable Long id){
        log.info("根据id查询试卷:{}",id);
        TestPaper testPaper = testPaperService.getTestPaperById(id);
        return Result.success(testPaper);
    }

    /**
     * 根据id修改试卷
     * @param testPaper
     * @return
     */
    @Operation(summary = "根据id修改试卷")
    @PutMapping
    public Result update(@RequestBody TestPaper testPaper){
        log.info("根据id修改试卷:{}", testPaper);
        testPaperService.update(testPaper);
        return Result.success();
    }
}
