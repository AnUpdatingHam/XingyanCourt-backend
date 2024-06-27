package org.rhythm.service;

import org.rhythm.dto.ArticleDTO;
import org.rhythm.dto.ArticlePageQueryDTO;
import org.rhythm.entity.*;
import org.rhythm.result.PageResult;
import org.rhythm.vo.ArticleExtensionVO;
import org.rhythm.vo.ArticleVO;

import java.util.List;

public interface ArticleService {
    void add(ArticleDTO articleDTO);

    void addCategory(ArticleCategory articleCategory);

    PageResult pageQuery(ArticlePageQueryDTO articlePageQueryDTO);

    void starOrStop(Integer status, Long id);

    Article getById(Long id);

    void update(ArticleDTO articleDTO);

    List<ArticleCategory> getCategories();

    ArticleExtension getExtensionById(Long id);

    void addExtension(ArticleExtension articleExtension);

    ArticleExtensionVO getExtensionVOById(Long id);

    ArticleVO getArticleVOById(Long id);

    ArticleCatalogue getCatalogueById(Long id);
}
