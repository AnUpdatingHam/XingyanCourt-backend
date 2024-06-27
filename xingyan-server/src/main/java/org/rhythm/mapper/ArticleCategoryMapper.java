package org.rhythm.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.rhythm.annotation.AutoFill;
import org.rhythm.entity.ArticleCategory;
import org.rhythm.enumeration.OperationType;

@Mapper
public interface ArticleCategoryMapper {
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into article_categories (name, introduction, status) " +
            "VALUES " +
            "(#{name},#{introduction},#{status})")
    void insert(ArticleCategory category);

    @Select("select * from article_categories where name = #{name}")
    ArticleCategory getByName(String name);

    @Select("select * from article_categories where id = #{id}")
    ArticleCategory getById(Long id);
}

















