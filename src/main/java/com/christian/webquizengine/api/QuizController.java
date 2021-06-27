package com.christian.webquizengine.api;

import com.christian.webquizengine.model.exception.PermissionDeniedException;
import com.christian.webquizengine.model.quiz.Answer;
import com.christian.webquizengine.model.quiz.Quiz;
import com.christian.webquizengine.model.quiz.QuizCompletion;
import com.christian.webquizengine.model.quiz.QuizResult;
import com.christian.webquizengine.service.completion.QuizCompletionService;
import com.christian.webquizengine.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@Validated
public class QuizController {

    private QuizService quizService;
    private QuizCompletionService quizCompletionService;

    @Autowired
    public QuizController(QuizService quizService, QuizCompletionService quizCompletionService) {
        this.quizService = quizService;
        this.quizCompletionService = quizCompletionService;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/api/quizzes")
    public Quiz postQuiz(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal UserDetails userDetails) {
        quiz.setAuthentication(userDetails.getUsername());
        return quizService.saveQuiz(quiz);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/api/quizzes/{id}/solve")
    public QuizResult solveQuiz(@PathVariable long id, @RequestBody Answer answer, @AuthenticationPrincipal UserDetails userDetails) {
        // debugging
//        System.out.println(id);
//        System.out.println(getQuizByID(id).getAnswer().toString());
//        System.out.println(answer.getAnswer().toString());
//        System.out.println(getQuizByID(id).getAnswer().toString().equals(answer.getAnswer().toString()));
        String correctAnswer = getQuizByID(id).getAnswer().toString();
        String submittedAnswer = answer.getAnswer().toString();
        boolean success = Objects.equals(correctAnswer, submittedAnswer);
        String submittedBy = userDetails.getUsername();
        String currentDate = LocalDateTime.now().toString();
        if (success) {
            quizCompletionService.saveQuizCompletion(
                    new QuizCompletion(id,currentDate, submittedBy)
            );
        }
        return new QuizResult(success);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/api/quizzes")
    public Page<Quiz> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pagesize) {
        return quizService.getAllQuizzes(page, pagesize);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizByID(@PathVariable long id) {
        return quizService.getQuizByID(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<HttpStatus> deleteQuizByID(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        Quiz quiz = quizService.getQuizByID(id);
        if (hasPermission(quiz.getAuthentication(), userDetails.getUsername())) {
            quizService.deleteQuizByID(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new PermissionDeniedException(quiz);
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/api/quizzes/{id}")
    public Quiz updateQuizID(@PathVariable long id, @Valid @RequestBody Quiz quiz, @AuthenticationPrincipal UserDetails userDetails) {
        // Make a delete request. If Error occurs (e.g., 403 Forbidden) method won't be executed
        // further anyway so no need to do any error handling here. Already managed my @DeleteMapping.
        deleteQuizByID(id, userDetails);
        quiz.setAuthentication(userDetails.getUsername());
        // Attempt to save quiz to database.
        return quizService.saveQuiz(quiz);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/api/quizzes/completed")
    public Page<QuizCompletion> getAllQuizCompletionsByUser(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pagesize,
            @AuthenticationPrincipal UserDetails userDetails) {
        return quizCompletionService.getAllQuizCompletionsByUser(page, pagesize, userDetails.getUsername());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/api/admin/{user}/completed")
    public Page<QuizCompletion> getAllQuizCompletionsByUserAdmin(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pagesize,
            @PathVariable String user) {
        return quizCompletionService.getAllQuizCompletionsByUser(page, pagesize, user);
    }

    public boolean hasPermission(String quizCreator, String currentUser) {
        // For debugging.
//        System.out.println("quizCreator: " + quizCreator + ", " + "currentUser: " + currentUser);
        return Objects.equals(quizCreator, currentUser);
    }

}
