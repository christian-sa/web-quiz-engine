package com.christian.webquizengine.service.quiz;

import com.christian.webquizengine.model.abs.AbstractQuizServiceImpl;
import com.christian.webquizengine.model.quiz.MyQuizCompletion;
import com.christian.webquizengine.repository.quiz.MyQuizCompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MyQuizCompletionServiceImpl extends AbstractQuizServiceImpl<MyQuizCompletion, Long>
        implements MyQuizCompletionService {

    private final MyQuizCompletionRepository myQuizCompletionRepository;

    @Autowired
    public MyQuizCompletionServiceImpl(MyQuizCompletionRepository myQuizCompletionRepository) {
        super(myQuizCompletionRepository);
        this.myQuizCompletionRepository = myQuizCompletionRepository;
    }

    @Override
    public Page<MyQuizCompletion> findAllByAuthentication(Integer pageNo, Integer pageSize, String authentication) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").ascending());
        return myQuizCompletionRepository.findAllByAuthentication(authentication, paging);
    }
}
