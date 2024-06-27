package org.rhythm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCatalogue {
    private Long articleId;
    private String numbers;
    private String contents;
    private String positions;
}
