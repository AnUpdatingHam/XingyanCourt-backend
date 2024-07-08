package org.rhythm.service;

import org.rhythm.dto.TestPaperCreateDTO;
import org.rhythm.entity.TestPaper;
import org.rhythm.vo.PDFCreateVO;

public interface AIService {
    PDFCreateVO createPDF(TestPaperCreateDTO testPaperCreateDTO);
}
