package org.rhythm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.constant.MessageConstant;
import org.rhythm.dto.TestPaperDTO;
import org.rhythm.dto.TestPaperPageQueryDTO;
import org.rhythm.entity.*;
import org.rhythm.exception.ArticleCategoryNotFoundException;
import org.rhythm.mapper.TestPaperMapper;
import org.rhythm.mapper.UserMapper;
import org.rhythm.result.PageResult;
import org.rhythm.service.TestPaperService;
import org.rhythm.vo.ArticleExtensionVO;
import org.rhythm.vo.ArticleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TestPaperServiceImpl implements TestPaperService {
    @Autowired
    private TestPaperMapper testPaperMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(TestPaper testPaper) {
        testPaperMapper.insert(testPaper);
    }

    @Override
    public PageResult pageQuery(TestPaperPageQueryDTO testPaperPageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(testPaperPageQueryDTO.getPage(), testPaperPageQueryDTO.getPageSize());
        Page<TestPaper> page = testPaperMapper.pageQuery(testPaperPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TestPaper getById(Long id) {
        TestPaper article = testPaperMapper.getById(id);
        return article;
    }

    @Override
    public void update(TestPaper testPaper) {
        testPaperMapper.update(testPaper);
    }

    @Override
    public TestPaper getTestPaperById(Long id) {
        return testPaperMapper.getById(id);
    }

}
