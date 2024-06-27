package org.rhythm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class CodeDTO implements Serializable {
    //ID
    @Schema(name = "code", type = "String")
    private String code;
}
