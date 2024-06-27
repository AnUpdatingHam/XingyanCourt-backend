package org.rhythm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCategory {
    private Long id;
    //分类名
    private String name;
    //简介
    private String introduction;
    //状态
    private Integer status;
}
