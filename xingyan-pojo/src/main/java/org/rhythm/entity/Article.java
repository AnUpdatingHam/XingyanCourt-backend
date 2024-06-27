package org.rhythm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Article implements Serializable {
    //ID
    private Long id;
    //作者ID
    private Long authorId;
    //作者姓名
    private String authorName;
    //标题
    private String title;
    //内容
    private String content;
    //分类ID
    private Long categoryId;
    //简介
    private String introduction;
    //标签
    private String tags;
    //状态
    private Integer status; //0被禁用
    //创建时间
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    //更新时间
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    //private Integer status;
}
