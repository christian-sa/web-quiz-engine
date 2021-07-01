package com.christian.webquizengine.service.quiz;

import com.christian.webquizengine.model.quiz.MyQuizCompletion;
import com.christian.webquizengine.model.abs.AbstractQuizService;
import org.springframework.data.domain.Page;


public interface MyQuizCompletionService extends AbstractQuizService<MyQuizCompletion, Long>  {

    Page<MyQuizCompletion> findAllByAuthentication(Integer pageNo, Integer pageSize, String authentication);
}
