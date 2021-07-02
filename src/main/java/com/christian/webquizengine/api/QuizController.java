package com.christian.webquizengine.api;

import com.christian.webquizengine.exception.PermissionDeniedException;
import com.christian.webquizengine.exception.QuizNotFoundException;
import com.christian.webquizengine.model.quiz.MyQuiz;
import com.christian.webquizengine.model.quiz.MyQuizCompletion;
import com.christian.webquizengine.model.quiz.QuizData;
import com.christian.webquizengine.model.quiz.QuizResult;
import com.christian.webquizengine.service.quiz.MyQuizCompletionService;
import com.christian.webquizengine.service.quiz.MyQuizService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
public class QuizController {

    private final MyQuizService myQuizService;
    private final MyQuizCompletionService myQuizCompletionService;

    @Autowired
    public QuizController(MyQuizService myQuizService, MyQuizCompletionService myQuizCompletionService) {
        this.myQuizService= myQuizService;
        this.myQuizCompletionService = myQuizCompletionService;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/api/quizzes")
    public MyQuiz postQuiz(@Valid @RequestBody QuizData quizData, @AuthenticationPrincipal UserDetails userDetails) {
        return myQuizService.save(
                new MyQuiz(quizData.getTitle(), quizData.getText(), quizData.getOptions(),
                        quizData.getAnswer(), userDetails.getUsername()));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(value = "/api/quizzes/{id}/solve")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public QuizResult solveQuiz(@PathVariable long id, @RequestBody List<Integer> answer, @AuthenticationPrincipal UserDetails userDetails) {
        myQuizService.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
        // Set a baseline for the correct answer, since it can be null. In that case, string remains empty.
        String correctAnswer = "";
        // Catch NPE and ignore it, since we already established a baseline for the correct answer.
        try {
            correctAnswer = myQuizService.findById(id).get().getAnswer().toString();
        } catch (Exception ignore) { }
        // If submitted List in RequestBody is empty, treat it as empty string.
        String submittedAnswer = answer.isEmpty() ? "" : answer.toString();
        // Because of the precautions we took, this should work correctly now.
        boolean success = Objects.equals(correctAnswer, submittedAnswer);
        String submittedBy = userDetails.getUsername();
        if (success) {
            myQuizCompletionService.save(
                    new MyQuizCompletion(submittedBy, id)
            );
        }
        return new QuizResult(success);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/api/quizzes")
    public Page<MyQuiz> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pagesize) {
        return myQuizService.findAll(page, pagesize);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/api/quizzes/{id}")
    public MyQuiz getQuizByID(@PathVariable long id) {
    return myQuizService.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<HttpStatus> deleteQuizByID(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (hasPermission(getQuizByID(id).getAuthentication(), userDetails.getUsername()) ||
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            myQuizService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new PermissionDeniedException(id);
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/api/quizzes/{id}")
    public void updateQuizID(@PathVariable long id, @Valid @RequestBody QuizData quizData, @AuthenticationPrincipal UserDetails userDetails) {
        if (hasPermission(getQuizByID(id).getAuthentication(), userDetails.getUsername()) ||
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            myQuizService.updateById(id, new MyQuiz(quizData, userDetails.getUsername()));
        } else {
            throw new PermissionDeniedException(id);
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/api/quizzes/completed")
    public Page<MyQuizCompletion> getAllQuizCompletionsByUser(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pagesize,
            @AuthenticationPrincipal UserDetails userDetails) {
        return myQuizCompletionService.findAllByAuthentication(page, pagesize, userDetails.getUsername());
    }

    public boolean hasPermission(String quizCreator, String currentUser) {
        return Objects.equals(quizCreator, currentUser);
    }


    /*
     * ADMIN MAPPING
     */

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/api/admin/{user}/completed")
    public Page<MyQuizCompletion> getAllQuizCompletionsByUserAdmin(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pagesize,
            @PathVariable String user) {
        return myQuizCompletionService.findAllByAuthentication(page, pagesize, user);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/api/admin/quizzes")
    public void deleteAllQuizzes() {
        myQuizService.deleteAll();
    }
}
