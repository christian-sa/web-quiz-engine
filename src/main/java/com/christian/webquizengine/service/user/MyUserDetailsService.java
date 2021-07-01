package com.christian.webquizengine.service.user;

import com.christian.webquizengine.exception.UserAlreadyExistsException;
import com.christian.webquizengine.model.user.MyUserDetails;
import com.christian.webquizengine.model.user.User;
import com.christian.webquizengine.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        // Configuration for admin user
        if (userRepository.findByEmail("superuser@mail.com").isEmpty()) {
            User superuser = new User();
            superuser.setEmail("superuser@mail.com");
            superuser.setPassword(bCryptPasswordEncoder.encode("admin"));
            superuser.setRoles("ROLE_ADMIN,ROLE_USER");
            userRepository.save(superuser);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(username, "User %s was not found.")));
        return new MyUserDetails(user);
    }

    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch(Exception e) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    public Optional<List<User>> getAllUsers() {
        return Optional.of(userRepository.findAll());
    }

    public Page<User> getAllUsers(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
        return userRepository.findAll(paging);
    }

}
