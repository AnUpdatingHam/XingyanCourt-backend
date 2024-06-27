package org.rhythm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.constant.MessageConstant;
import org.rhythm.dto.ArticleDTO;
import org.rhythm.dto.ArticlePageQueryDTO;
import org.rhythm.entity.*;
import org.rhythm.exception.ArticleCategoryNotFoundException;
import org.rhythm.mapper.ArticleCatalogueMapper;
import org.rhythm.mapper.ArticleCategoryMapper;
import org.rhythm.mapper.ArticleMapper;
import org.rhythm.mapper.UserMapper;
import org.rhythm.result.PageResult;
import org.rhythm.service.ArticleService;
import org.rhythm.vo.ArticleExtensionVO;
import org.rhythm.vo.ArticleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleCategoryMapper categoryMapper;
    @Autowired
    private ArticleCatalogueMapper catalogueMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(ArticleDTO articleDTO) {
        User author = userMapper.getById(articleDTO.getAuthorId());
        Article article = Article.builder()
                .authorName(author.getUsername())
                .categoryId(articleDTO.getCategoryId())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(articleDTO, article);

        ArticleCategory category = categoryMapper.getById(articleDTO.getCategoryId());
        if(category == null){
            throw new ArticleCategoryNotFoundException(MessageConstant.ARTICLE_CATEGORY_NOT_FOUNT + " : " + articleDTO.getCategoryId());
        }

        ArticleCatalogue catalogue = ArticleCatalogue.builder()
                .articleId(article.getId())
                .numbers(articleDTO.getCatalogueNumbers())
                .contents(articleDTO.getCatalogueContents())
                .positions(articleDTO.getCataloguePositions())
                .build();
        catalogueMapper.insert(catalogue);
        log.info("article : {}", article);
        log.info("category : {}", category);
        log.info("catalogue : {}", catalogue);

        articleMapper.insert(article);
    }

    @Override
    public void addCategory(ArticleCategory articleCategory) {
        categoryMapper.insert(articleCategory);
    }

    @Override
    public PageResult pageQuery(ArticlePageQueryDTO articlePageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(articlePageQueryDTO.getPage(), articlePageQueryDTO.getPageSize());
        Page<ArticleExtensionVO> page = articleMapper.pageQueryExtension(articlePageQueryDTO);
        for (ArticleExtensionVO articleExtensionVO : page.getResult()) {
            ArticleCategory category = categoryMapper.getById(articleExtensionVO.getCategoryId());
            articleExtensionVO.setCategoryName(category.getName());
            articleExtensionVO.setCategoryIntroduction(category.getIntroduction());

            ArticleExtension extension = getExtensionById(articleExtensionVO.getId());
            BeanUtils.copyProperties(extension, articleExtensionVO);
        }
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void starOrStop(Integer status, Long id) {
        Article article = Article.builder()
                .status(status)
                .id(id)
                .build();
        articleMapper.update(article);
    }

    @Override
    public Article getById(Long id) {
        Article article = articleMapper.getById(id);
        return article;
    }

    @Override
    public void update(ArticleDTO articleDTO) {
        User author = userMapper.getById(articleDTO.getAuthorId());
        Article article = Article.builder()
                .authorName(author.getUsername())
                .categoryId(articleDTO.getCategoryId())
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(articleDTO, article);

        ArticleCategory category = categoryMapper.getById(articleDTO.getCategoryId());
        if(category == null){
            throw new ArticleCategoryNotFoundException(MessageConstant.ARTICLE_CATEGORY_NOT_FOUNT + " : " + articleDTO.getCategoryId());
        }
        articleMapper.update(article);

        ArticleCatalogue catalogue = ArticleCatalogue.builder()
                .articleId(article.getId())
                .numbers(articleDTO.getCatalogueNumbers())
                .contents(articleDTO.getCatalogueContents())
                .positions(articleDTO.getCataloguePositions())
                .build();
        catalogueMapper.update(catalogue);
    }

    @Override
    public List<ArticleCategory> getCategories() {
        List<ArticleCategory> categoryList = catalogueMapper.list();
        log.info("ArticleCategoryList : {}", categoryList);
        return categoryList;
    }

    @Override
    public ArticleExtension getExtensionById(Long id) {
        ArticleExtension extension = articleMapper.getExtensionById(id);
        return extension;
    }

    @Override
    public void addExtension(ArticleExtension articleExtension) {
        articleMapper.insertExtension(articleExtension);
    }

    @Override
    public ArticleExtensionVO getExtensionVOById(Long id) {
        ArticleVO articleVO = getArticleVOById(id);
        ArticleExtension extension = getExtensionById(id);
        ArticleExtensionVO articleExtensionVO = new ArticleExtensionVO();
        BeanUtils.copyProperties(articleVO, articleExtensionVO);
        BeanUtils.copyProperties(extension, articleExtensionVO);
        return articleExtensionVO;
    }

    @Override
    public ArticleVO getArticleVOById(Long id) {
        Article article = getById(id);
        ArticleCategory category = categoryMapper.getById(article.getCategoryId());
        ArticleVO articleVO = ArticleVO.builder()
                .categoryName(category.getName())
                .categoryIntroduction(category.getIntroduction())
                .build();
        BeanUtils.copyProperties(article, articleVO);
        return articleVO;
    }

    @Override
    public ArticleCatalogue getCatalogueById(Long id) {
        ArticleCatalogue articleCatalogue = catalogueMapper.getById(id);
        return articleCatalogue;
    }
}
