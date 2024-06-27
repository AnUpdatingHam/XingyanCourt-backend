package org.rhythm.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.rhythm.annotation.AutoFill;
import org.rhythm.dto.TestPaperPageQueryDTO;
import org.rhythm.entity.TestPaper;
import org.rhythm.enumeration.OperationType;

@Mapper
public interface TestPaperMapper {
    /**
     * 插入数据
     * @param testPaper
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into test_paper (user_id, subject, grade, type, page_count," +
            " question_count, contain_answer, path, create_time) " +
            "VALUES " +
            "(#{userId},#{subject},#{grade},#{type},#{pageCount},#{questionCount}," +
            "#{containAnswer},#{path},#{createTime})")
    void insert(TestPaper testPaper);

    /**
     * 分页查询
     * @param testPaperPageQueryDTO
     * @return
     */
    Page<TestPaper> pageQuery(TestPaperPageQueryDTO testPaperPageQueryDTO);

    /**
     * 根据主键动态修改属性
     * @param testPaper
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(TestPaper testPaper);

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    @Select("select * from test_paper where id = #{id}")
    TestPaper getById(Long id);
}
