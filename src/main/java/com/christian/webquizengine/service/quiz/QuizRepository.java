package com.christian.webquizengine.service.quiz;

import com.christian.webquizengine.model.quiz.Quiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Quiz Repository Interface.
 */
@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {

    /**
     * Finds the answer for the quiz associated with the given ID.
     * @param id Quiz' ID.
     * @return List of the answer.
     */
    @Query("SELECT a FROM Quiz q JOIN q.answer a WHERE q.id = :id")
    Optional<List<Integer>> findQuizAnswerById(@Param("id") long id);
}
