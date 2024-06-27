package org.rhythm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.io.Serializable;

@Data
@Tag(name = "用户查询界面DTO", description = "查询用户界面传递的数据模型")
public class UserPageQueryDTO implements Serializable {
    //用户姓名
    @Schema(name = "用户名", type = "String")
    private String username;

    //页码
    @Schema(name = "页码", type = "Integer")
    private Integer page;

    //每页显示记录数
    @Schema(name = "每页记录数", type = "Integer")
    private Integer pageSize;
}
