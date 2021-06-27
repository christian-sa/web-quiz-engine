package com.christian.webquizengine.service.user;

import com.christian.webquizengine.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * User Repository Interface.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds user by Email.
     * @param email Users' Email.
     * @return Optional of the user.
     *         If there is no user with the specified Email, Optional is empty.
     */
    Optional<User> findByEmail(String email);
}
