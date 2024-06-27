package org.rhythm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.io.Serializable;

@Data
@Tag(name = "问题DTO", description = "问AI问题时传递的数据模型")
public class QuestionDTO {
    @Schema(name = "问题", type = "String")
    private String question;
}