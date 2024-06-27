package org.rhythm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    //ID
    @Schema(name = "ID", type = "Long")
    private Long id;
    //用户名
    @Schema(name = "用户名", type = "String")
    private String username;
    //密码
    @Schema(name = "密码", type = "String")
    private String password;
    //手机号
    @Schema(name = "电话号码", type = "String")
    private String phone;
    //邮箱
    @Schema(name = "邮箱", type = "String")
    private String email;
    //简介
    @Schema(name = "简介", type = "String")
    private String introduction;
    //状态
    @Schema(name = "状态", type = "Integer")
    private Integer status;
}
