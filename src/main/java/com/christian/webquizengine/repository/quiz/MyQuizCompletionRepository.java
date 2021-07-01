package com.christian.webquizengine.repository.quiz;

import com.christian.webquizengine.model.abs.AbstractQuizRepository;
import com.christian.webquizengine.model.quiz.MyQuizCompletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyQuizCompletionRepository extends AbstractQuizRepository<MyQuizCompletion, Long> {

    @Query("SELECT qc FROM QuizCompletion qc WHERE qc.authentication =:auth")
    Page<MyQuizCompletion> findAllByAuthentication(@Param("auth") String auth, Pageable pageable);
}
