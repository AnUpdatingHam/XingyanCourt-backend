package org.rhythm.service;

import org.rhythm.dto.TestPaperCreateDTO;
import org.rhythm.dto.TestPaperPageQueryDTO;
import org.rhythm.entity.*;
import org.rhythm.result.PageResult;
import org.rhythm.result.Result;
import org.springframework.core.io.Resource;

import java.util.List;

public interface TestPaperService {
    void add(TestPaper testPaper);

    PageResult pageQuery(TestPaperPageQueryDTO testPaperPageQueryDTO);

    TestPaper getById(Long id);

    void update(TestPaper testPaper);

    TestPaper getTestPaperById(Long id);

    void deleteBatch(List<Long> ids);

    Result<Resource> downloadPDF(String filename);
}
