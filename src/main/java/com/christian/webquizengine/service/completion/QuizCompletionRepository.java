package com.christian.webquizengine.service.completion;

import com.christian.webquizengine.model.quiz.QuizCompletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Quiz Completion Repository Interface.
 */
@Repository
public interface QuizCompletionRepository extends PagingAndSortingRepository<QuizCompletion, Long> {

    @Query("SELECT qc FROM QuizCompletion qc WHERE qc.authentication =:auth")
    Page<QuizCompletion> findAllByAuthentication(@Param("auth") String auth, Pageable pageable);
}
