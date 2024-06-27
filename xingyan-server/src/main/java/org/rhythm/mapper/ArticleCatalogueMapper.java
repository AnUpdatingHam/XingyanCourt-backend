package org.rhythm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.rhythm.annotation.AutoFill;
import org.rhythm.entity.ArticleCatalogue;
import org.rhythm.entity.ArticleCategory;
import org.rhythm.enumeration.OperationType;

import java.util.List;

@Mapper
public interface ArticleCatalogueMapper {
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into article_catalogue (numbers, contents, positions) " +
            "VALUES " +
            "(#{numbers},#{contents},#{positions})")
    void insert(ArticleCatalogue catalogue);


    @AutoFill(value = OperationType.UPDATE)
    void update(ArticleCatalogue catalogue);

    List<ArticleCategory> list();

    @Select("select * from article_catalogue where article_id = #{id}")
    ArticleCatalogue getById(Long id);
}
