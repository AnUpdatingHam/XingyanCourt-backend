package org.rhythm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "问题VO", description = "AI模型返回的回答")
public class QuestionVO {
    @Schema(name = "回答", type = "String")
    private String answer;
}

