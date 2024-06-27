package org.rhythm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserExtension implements Serializable {
    //ID
    private Long id;
    //用户名
    private String username;
    //经验值
    private int exp;
    //金币数
    private int coin;
    //图片url
    private String imageUrl;
}
