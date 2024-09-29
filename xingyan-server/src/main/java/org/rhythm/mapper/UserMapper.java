package org.rhythm.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.rhythm.annotation.AutoFill;
import org.rhythm.dto.UserPageQueryDTO;
import org.rhythm.entity.User;
import org.rhythm.enumeration.OperationType;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into user (username, password, phone, email, status, coins, diamonds, create_time, update_time, status, image_url) " +
            "VALUES " +
            "(#{username},#{password},#{phone},#{email},#{status},#{coins},#{diamonds},#{createTime},#{updateTime},#{status},#{imageUrl})")
    void insert(User user);

    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    Page<User> pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 根据主键动态修改属性
     * @param User
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(User User);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long id);
}
