package org.rhythm.service;

import org.rhythm.dto.TestPaperDTO;
import org.rhythm.dto.TestPaperPageQueryDTO;
import org.rhythm.entity.*;
import org.rhythm.result.PageResult;
import org.rhythm.vo.ArticleVO;

public interface TestPaperService {
    void add(TestPaper testPaper);

    PageResult pageQuery(TestPaperPageQueryDTO testPaperPageQueryDTO);

    TestPaper getById(Long id);

    void update(TestPaper testPaper);

    TestPaper getTestPaperById(Long id);
}
