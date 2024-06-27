package org.rhythm.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.rhythm.annotation.AutoFill;
import org.rhythm.dto.ArticlePageQueryDTO;
import org.rhythm.entity.Article;
import org.rhythm.entity.ArticleCategory;
import org.rhythm.entity.ArticleExtension;
import org.rhythm.entity.User;
import org.rhythm.enumeration.OperationType;
import org.rhythm.vo.ArticleExtensionVO;
import org.rhythm.vo.ArticleVO;

@Mapper
public interface ArticleMapper {
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into article (title, content, category_id, introduction, status, tags, create_time, update_time) " +
            "VALUES " +
            "(#{title},#{content},#{categoryId},#{introduction},#{status},#{tags},#{createTime},#{updateTime})")
    void insert(Article article);

    /**
     * 分页查询
     * @param articlePageQueryDTO
     * @return
     */
    Page<Article> pageQuery(ArticlePageQueryDTO articlePageQueryDTO);

    /**
     * 根据主键动态修改属性
     * @param article
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Article article);

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    @Select("select * from article where id = #{id}")
    Article getById(Long id);

    @Select("select * from article_extension where id = #{id}")
    ArticleExtension getExtensionById(Long id);

    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into article_extension (id, likes, collections, image_url) " +
            "VALUES " +
            "(#{id},#{likes},#{collections},#{imageUrl})")
    void insertExtension(ArticleExtension articleExtension);

    Page<ArticleExtensionVO> pageQueryExtension(ArticlePageQueryDTO articlePageQueryDTO);
}
