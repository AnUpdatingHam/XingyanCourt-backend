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
@Tag(name = "PDF创建响应VO", description = "AI模型返回的参数")
public class PDFCreateVO {
    @Schema(name = "PDF文件路径", type = "String")
    String filepath;
}
