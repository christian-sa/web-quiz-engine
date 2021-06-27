package com.christian.webquizengine.service.completion;

import com.christian.webquizengine.model.quiz.QuizCompletion;
import org.springframework.data.domain.Page;

/**
 * Quiz Completion Service Interface.
 */
public interface QuizCompletionService {

    void saveQuizCompletion(QuizCompletion quizCompletion);

    Page<QuizCompletion> getAllQuizCompletionsByUser(Integer pageNo, Integer pageSize, String authentication);
}
