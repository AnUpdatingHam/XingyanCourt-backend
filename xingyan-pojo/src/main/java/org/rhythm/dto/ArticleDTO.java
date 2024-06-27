package org.rhythm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleDTO implements Serializable {
    //ID
    @Schema(name = "ID", type = "Long")
    private Long id;
    //作者ID
    @Schema(name = "作者ID", type = "Long")
    private Long authorId;
    //标题
    @Schema(name = "标题", type = "String")
    private String title;
    //内容
    @Schema(name = "内容", type = "String")
    private String content;
    //分类ID
    @Schema(name = "分类ID", type = "Long")
    private Long categoryId;
    //简介
    @Schema(name = "简介", type = "String")
    private String introduction;
    //标签
    @Schema(name = "文章标签", type = "String")
    private String tags;
    //状态
    @Schema(name = "状态", type = "Integer")
    private Integer status; //0被禁用

    @Schema(name = "目录编号", type = "String")
    private String catalogueNumbers;
    @Schema(name = "目录内容", type = "String")
    private String catalogueContents;
    @Schema(name = "目录位置", type = "String")
    private String cataloguePositions;
}
