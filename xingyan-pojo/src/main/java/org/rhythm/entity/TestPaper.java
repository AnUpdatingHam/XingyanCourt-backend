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
public class TestPaper implements Serializable {
    //ID
    private Long id;
    //名字
    private String name;
    //用户ID
    private Long userId;
    //科目
    private String subject;
    //年级
    private String grade;
    //考试类型
    private String type;
    //页数
    private short pageCount;
    //题数
    private int questionCount;
    //包含答案
    private boolean containAnswer;
    //磁盘路径
    private String path;
    //创建时间
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
