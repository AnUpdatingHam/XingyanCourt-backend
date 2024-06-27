package org.rhythm.entity;

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
public class ArticleExtension implements Serializable {
    //ID
    private Long id;
    //点赞
    private Integer likes;
    //收藏
    private Integer collections;
    //图片url
    private String imageUrl;
}
