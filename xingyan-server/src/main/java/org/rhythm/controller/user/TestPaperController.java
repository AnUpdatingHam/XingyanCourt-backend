package org.rhythm.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.dto.TestPaperCreateDTO;
import org.rhythm.dto.TestPaperPageQueryDTO;
import org.rhythm.entity.TestPaper;
import org.rhythm.result.PageResult;
import org.rhythm.result.Result;
import org.rhythm.service.AIService;
import org.rhythm.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
    @Autowired
    private AIService aiService;

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

    /**
     * pdf批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @Operation(summary = "pdf批量删除")
    public Result delete(@RequestParam List<Long> ids){
        log.info("pdf批量删除: {}",ids);
        testPaperService.deleteBatch(ids);
        return Result.success();
    }


    /**
     *
     * @param filename
     * @return
     */
    @GetMapping("/download/{filename}")
    @Operation(summary = "pdf下载")
    public Result<Resource> downloadFile(@PathVariable String filename){
        log.info("pdf下载:{}", filename);
        return testPaperService.downloadPDF(filename);
    }

}
