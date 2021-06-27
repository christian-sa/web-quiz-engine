package com.christian.webquizengine.api;

import com.christian.webquizengine.model.exception.IllegalEmailException;
import com.christian.webquizengine.model.user.User;
import com.christian.webquizengine.service.user.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class UserController {

    private final MyUserDetailsService myUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    @Autowired
    public UserController(MyUserDetailsService myUserDetailsService, PasswordEncoder passwordEncoder) {
        this.myUserDetailsService = myUserDetailsService;
    }

    /**
     * POST request to register a new user.
     * @param user Instance of User.
     */
    @PostMapping("/api/register")
    public void registerUser(@Valid @RequestBody User user) {
        /*
         * Regex to check if the Email is valid (e.g., user@example.com).
         * If Email validation fails, throw IllegalEmailException.
         */
        if (!user.getEmail().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            throw new IllegalEmailException(user.getEmail());
        }
        // Set the authority to represent a user.
        user.setRoles("ROLE_USER");
        // Encrypt the password.
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // Attempt to save user to database.
        myUserDetailsService.saveUser(user);
    }

    /**
     * GET request to return a list of all registered users.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/users")
    public Optional<List<User>> getAllUsers() {
        return myUserDetailsService.getAllUsers();
    }
}

