package org.rhythm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.rhythm.entity.UserExtension;

@Mapper
public interface UserExtensionMapper {
    @Select("select * from user_extension where id = #{id}")
    UserExtension getById(Long id);

}
