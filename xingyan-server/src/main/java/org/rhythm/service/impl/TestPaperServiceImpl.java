package org.rhythm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.rhythm.dto.TestPaperCreateDTO;
import org.rhythm.dto.TestPaperPageQueryDTO;
import org.rhythm.entity.*;
import org.rhythm.mapper.TestPaperMapper;
import org.rhythm.result.PageResult;
import org.rhythm.result.Result;
import org.rhythm.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Service
public class TestPaperServiceImpl implements TestPaperService {
    @Autowired
    private TestPaperMapper testPaperMapper;

    @Override
    public void add(TestPaper testPaper) {
        testPaperMapper.insert(testPaper);
    }

    @Override
    public PageResult pageQuery(TestPaperPageQueryDTO testPaperPageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(testPaperPageQueryDTO.getPage(), testPaperPageQueryDTO.getPageSize());
        Page<TestPaper> page = testPaperMapper.pageQuery(testPaperPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
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

    @Override
    public void deleteBatch(List<Long> ids) {
        testPaperMapper.deleteByIds(ids);
    }

    @Override
    public Result<Resource> downloadPDF(String filename){
        System.out.println("url = " + "user_pdfs\\" + filename);
        try {
            Resource resource = new UrlResource("file:user_pdfs\\" + filename);
            if (resource.exists() && resource.isReadable()) {
                return Result.success(resource); //包含了文件资源的result对象
            } else {
                return Result.error("文件不存在");
            }
        } catch (MalformedURLException e) {
            return Result.error("URL格式错误");
        }
    }
}
