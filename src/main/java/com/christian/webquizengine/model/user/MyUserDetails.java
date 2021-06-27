package com.christian.webquizengine.model.user;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User Details Implementation.
 */
@Primary
public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean enabled;
    private List<GrantedAuthority> authorities;

    /**
     * Initialize UserDetails from the given User.
     * Username in UserDetails represents the Users Email.
     * Authorities were previously stored as CSV in String format and are now converted to a list.
     * @param user Instance of User.
     */
    public MyUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Get authorities.
     * @return List of authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Get password.
     * @return password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Get username.
     * @return username.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * This functionality isn't implemented.
     * @return always true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * This functionality isn't implemented.
     * @return always true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * This functionality isn't implemented.
     * @return always true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if enabled.
     * @return true if enabled.
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
