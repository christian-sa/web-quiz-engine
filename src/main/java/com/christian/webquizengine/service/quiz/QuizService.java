package com.christian.webquizengine.service.quiz;

import com.christian.webquizengine.model.quiz.Quiz;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * Quiz Service interface.
 */
public interface QuizService {

    /**
     * Save a quiz.
     * @param quiz instance of Quiz.
     * @return saved quiz.
     */
    Quiz saveQuiz(Quiz quiz);

    /**
     * Get all quizzes.
     * @return List of all quizzes.
     */
    List<Quiz> getAllQuizzes();

    /**
     * Get quiz by ID.
     * @param id Quiz' ID.
     * @return quiz associated with the ID.
     */
    Quiz getQuizByID(Long id);

    /**
     * Get answer by ID.
     * @param id Quiz' ID.
     * @return List of the answer.
     */
    List<Integer> getQuizAnswerByID(Long id);

    /**
     * Delete quiz by ID.
     * @param id Quiz' ID.
     */
    void deleteQuizByID(Long id);

    /**
     * Get all quizzes with paging.
     * @param pageNo Page number (starts from 0)
     * @param pageSize Page size
     * @return Page of quizzes.
     */
    Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize);
}
