package org.rhythm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestPaperDTO implements Serializable {
    //ID
    @Schema(name = "ID", type = "Long")
    private Long id;
    //作者ID
    @Schema(name = "用户ID", type = "Long")
    private Long userId;
    //标题
    @Schema(name = "科目", type = "String")
    private String subject;
    //内容
    @Schema(name = "年级", type = "String")
    private String grade;
    //简介
    @Schema(name = "考试类型", type = "String")
    private String type;
}
