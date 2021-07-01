package com.christian.webquizengine.service.quiz;

import com.christian.webquizengine.exception.QuizNotFoundException;
import com.christian.webquizengine.model.abs.AbstractQuizServiceImpl;
import com.christian.webquizengine.model.quiz.MyQuiz;
import com.christian.webquizengine.repository.quiz.MyQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class MyQuizServiceImpl extends AbstractQuizServiceImpl<MyQuiz, Long>
        implements MyQuizService {

    private final MyQuizRepository myQuizRepository;

    @Autowired
    public MyQuizServiceImpl(MyQuizRepository myQuizRepository) {
        super(myQuizRepository);
        this.myQuizRepository = myQuizRepository;
    }

    @Override
    public void updateById(Long id, MyQuiz myQuiz) {
        myQuizRepository.updateById(
                id,myQuizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id)).getVersion() + 1,
                LocalDateTime.now(), myQuiz.getTitle(), myQuiz.getText(), myQuiz.getOptionsAsCSV(), myQuiz.getAnswerAsCSV()
        );
    }
}