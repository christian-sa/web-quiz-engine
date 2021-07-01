package com.christian.webquizengine.repository.quiz;

import com.christian.webquizengine.model.abs.AbstractQuizRepository;
import com.christian.webquizengine.model.quiz.MyQuiz;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface MyQuizRepository extends AbstractQuizRepository<MyQuiz, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Quiz q SET q.updatedAt = :time, q.version = :version, q.title = :title, " +
            "q.text = :text, q.options = :options, q.answer = :answer WHERE q.id = :id")
    void updateById(
            @Param("id") long id, @Param("version") int version, @Param("time") LocalDateTime time,
            @Param("title") String title, @Param("text") String text, @Param("options") String options, @Param("answer") String answer
    );
}
