package com.christian.webquizengine.service.quiz;

import com.christian.webquizengine.model.quiz.MyQuiz;
import com.christian.webquizengine.model.abs.AbstractQuizService;

public interface MyQuizService extends AbstractQuizService<MyQuiz, Long> {

    void updateById(Long id, MyQuiz myQuiz);
}



