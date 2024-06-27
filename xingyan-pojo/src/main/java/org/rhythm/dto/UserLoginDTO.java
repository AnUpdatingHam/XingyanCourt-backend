package org.rhythm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
@Tag(name = "用户DTO", description = "用户登录时传递的数据模型")
public class UserLoginDTO implements Serializable {

    @Schema(name = "用户名", type = "String")
    private String username;

    @Schema(name = "密码", type = "String")
    private String password;
}
