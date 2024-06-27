package org.rhythm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "用户登录VO", description = "用户登录返回的数据模型")
public class UserLoginVO implements Serializable {

    @Schema(name = "主键值", type = "Long")
    private Long id;

    @Schema(name = "用户名", type = "String")
    private String username;

    @Schema(name = "Jwt令牌", type = "String")
    private String token;

    @Schema(name = "用户创建时间", type = "LocalDateTime")
    private LocalDateTime createTime;

    @Schema(name = "用户更新时间", type = "LocalDateTime")
    private LocalDateTime updateTime;
}
