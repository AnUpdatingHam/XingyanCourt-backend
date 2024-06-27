package org.rhythm.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.dto.ArticleDTO;
import org.rhythm.dto.ArticlePageQueryDTO;
import org.rhythm.entity.Article;
import org.rhythm.entity.ArticleCatalogue;
import org.rhythm.entity.ArticleCategory;
import org.rhythm.entity.ArticleExtension;
import org.rhythm.result.PageResult;
import org.rhythm.result.Result;
import org.rhythm.service.ArticleService;
import org.rhythm.vo.ArticleExtensionVO;
import org.rhythm.vo.ArticleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//文章接口
@CrossOrigin
@RequestMapping("/user/article")
@Tag(name = "文章接口", description = "文章的新增、查询、删除")
@RestController
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     *新增文章
     * @param articleDTO
     * @return
     */
    @Operation(summary = "新增文章")
    @PostMapping
    public Result add(@RequestBody ArticleDTO articleDTO){
        log.info("新增文章: {}", articleDTO);
        articleService.add(articleDTO);
        return Result.success();
    }

    /**
     *新增文章分类
     * @param articleCategory
     * @return
     */
    @Operation(summary = "新增文章分类")
    @PostMapping("/category")
    public Result add(@RequestBody ArticleCategory articleCategory){
        log.info("新增文章分类: {}", articleCategory);
        articleService.addCategory(articleCategory);
        return Result.success();
    }

    /**
     *新增文章拓展信息
     * @param articleExtension
     * @return
     */
    @Operation(summary = "新增文章拓展信息")
    @PostMapping("/extension")
    public Result add(@RequestBody ArticleExtension articleExtension){
        log.info("新增文章分类: {}", articleExtension);
        articleService.addExtension(articleExtension);
        return Result.success();
    }

    /**
     * 查询所有分类
     * @return
     */
    @Operation(summary = "查询所有文章分类")
    @GetMapping("/category")
    public Result<List<ArticleCategory>> getCategories(){
        log.info("查询所有文章分类");
        List<ArticleCategory> categoryList = articleService.getCategories();
        return Result.success(categoryList);
    }

    /**
     * 分页查询文章
     * @param articlePageQueryDTO
     * @return
     */
    @Operation(summary = "分页查询文章")
    @GetMapping("/page")
    public Result<PageResult> page(ArticlePageQueryDTO articlePageQueryDTO){
        log.info("分页查询文章，参数为：{}",articlePageQueryDTO);
        PageResult pageResult = articleService.pageQuery(articlePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用、禁用文章账号
     * @param status
     * @param id
     * @return
     */
    @Operation(summary = "启用、禁用文章账号")
    @PostMapping("/status/{id}/{status}")
    public Result startOrStop(@PathVariable  Long id, @PathVariable Integer status){
        log.info("启用、禁用文章账号: {}.{}",status,id);
        articleService.starOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询文章")
    @GetMapping("/{id}")
    public Result<ArticleVO> getById(@PathVariable Long id){
        log.info("根据id查询文章:{}",id);
        ArticleVO articleVO = articleService.getArticleVOById(id);
        return Result.success(articleVO);
    }

    /**
     * 根据id查询文章目录
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询文章目录")
    @GetMapping("/catalogue/{id}")
    public Result<ArticleCatalogue> getCatalogueById(@PathVariable Long id){
        log.info("根据id查询文章:{}",id);
        ArticleCatalogue articleCatalogue = articleService.getCatalogueById(id);
        return Result.success(articleCatalogue);
    }

    /**
     * 根据id查询文章拓展信息
     * @param id
     * @return
     */
    @Operation(summary = "根据id查询文章拓展信息")
    @GetMapping("/extension/{id}")
    public Result<ArticleExtensionVO> getExtensionById(@PathVariable Long id){
        log.info("根据id查询文章拓展信息:{}",id);
        ArticleExtensionVO articleExtensionVO = articleService.getExtensionVOById(id);
        return Result.success(articleExtensionVO);
    }

    /**
     * 根据id修改用户
     * @param articleDTO
     * @return
     */
    @Operation(summary = "根据id修改文章")
    @PutMapping
    public Result update(@RequestBody ArticleDTO articleDTO){
        log.info("根据id修改文章:{}",articleDTO);
        articleService.update(articleDTO);
        return Result.success();
    }
}
