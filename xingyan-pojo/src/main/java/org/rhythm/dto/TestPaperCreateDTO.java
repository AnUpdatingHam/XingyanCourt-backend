package org.rhythm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestPaperCreateDTO implements Serializable {
    //作者ID
    @Schema(name = "用户ID", type = "Long")
    private Long userId;
    //试卷名
    @Schema(name = "试卷名", type = "Long")
    private String name;
    //标题
    @Schema(name = "科目", type = "String")
    private String subject;
    //内容
    @Schema(name = "年级", type = "String")
    private String grade;
    //简介
    @Schema(name = "考试类型", type = "String")
    private String type;
    //是否含答案
    @Schema(name = "是否含答案", type = "boolean")
    private boolean containAnswer;
    //页数
    @Schema(name = "页数", type = "short")
    private short pageCount;
    //题数
    @Schema(name = "问题数", type = "int")
    private int questionCount;
    //补充
    @Schema(name = "补充说明", type = "String")
    private String supplement;
    //完整指令
    @Schema(name = "完整指令", type = "String")
    private String instruction;
}
