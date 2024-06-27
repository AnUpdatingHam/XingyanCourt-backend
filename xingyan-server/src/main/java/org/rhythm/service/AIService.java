package org.rhythm.service;

import org.rhythm.entity.Question;
import org.rhythm.vo.QuestionVO;

import java.io.IOException;

public interface AIService {
    QuestionVO answer(Question question);

    String getAccessToken() throws IOException;
}
