package org.rhythm.service.impl;

import org.apache.poi.ss.formula.functions.T;
import org.rhythm.dto.TestPaperCreateDTO;
import org.rhythm.entity.TestPaper;
import org.rhythm.service.AIService;
import org.rhythm.service.TestPaperService;
import org.rhythm.utils.AIUtil;
import org.rhythm.vo.PDFCreateVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AIServiceImpl implements AIService {
    @Autowired
    AIUtil aiUtil;
    @Autowired
    TestPaperService testPaperService;

    @Override
    public PDFCreateVO createPDF(TestPaperCreateDTO testPaperCreateDTO) {
        String filepath = aiUtil.generatePDF(testPaperCreateDTO.getInstruction());

        TestPaper testPaper = new TestPaper();
        BeanUtils.copyProperties(testPaperCreateDTO, testPaper);
        testPaper.setPath(filepath);
        testPaper.setCreateTime(LocalDateTime.now());
        testPaperService.add(testPaper);

        return new PDFCreateVO(filepath);
    }
}
