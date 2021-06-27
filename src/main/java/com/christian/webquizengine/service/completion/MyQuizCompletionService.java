package com.christian.webquizengine.service.completion;

import com.christian.webquizengine.model.quiz.QuizCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Quiz Completion Service Implementation
 */
@Service
public class MyQuizCompletionService implements QuizCompletionService{

    private QuizCompletionRepository quizCompletionRepository;

    @Autowired
    public MyQuizCompletionService(QuizCompletionRepository quizCompletionRepository) {
        this.quizCompletionRepository = quizCompletionRepository;
    }

    @Override
    public Page<QuizCompletion> getAllQuizCompletionsByUser(Integer pageNo, Integer pageSize, String authentication) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("completedAt").descending());
        return quizCompletionRepository.findAllByAuthentication(authentication, paging);
    }

    @Override
    public void saveQuizCompletion(QuizCompletion quizCompletion) {
        quizCompletionRepository.save(quizCompletion);
    }
}
