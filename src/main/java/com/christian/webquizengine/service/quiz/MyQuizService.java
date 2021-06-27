package com.christian.webquizengine.service.quiz;

import com.christian.webquizengine.model.exception.QuizNotFoundException;
import com.christian.webquizengine.model.quiz.Quiz;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Quiz Service Implementation.
 */
@Service
@NoArgsConstructor
public class MyQuizService implements QuizService {

    private QuizRepository quizRepository;

    @Autowired
    public MyQuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    /**
     * Save a quiz.
     * @param quiz instance of Quiz.
     * @return saved quiz.
     */
    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    /**
     * Get all quizzes.
     * @return List of all quizzes.
     */
    @Override
    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    /**
     * Get quiz by ID.
     * @param id Quiz' ID.
     * @return quiz associated with the ID.
     */
    @Override
    public Quiz getQuizByID(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
    }

    /**
     * Get quiz answer by ID.
     * @param id Quiz' ID.
     * @return List of the answer. If Quiz with the given ID doesnt exist, throw QuizNotFoundException.
     */
    @Override
    public List<Integer> getQuizAnswerByID(Long id) {
        return quizRepository.findQuizAnswerById(id).orElseThrow(() -> new QuizNotFoundException(id));
    }

    /**
     * Delete quiz by ID.
     * @param id Quiz' ID.
     */
    @Override
    public void deleteQuizByID(Long id) {
        quizRepository.deleteById(id);
    }

    /**
     * Get all quizzes with paging.
     * @param pageNo Page number (starts from 0)
     * @param pageSize Page size
     * @return Page of quizzes.
     */
    @Override
    public Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return quizRepository.findAll(paging);
    }
}
