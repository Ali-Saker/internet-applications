package com.internetapplications.security;

import com.internetapplications.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<User> {
    public AuditorAwareImpl() {
    }

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication == null ? null : SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal != null && authentication.isAuthenticated() && principal instanceof User ? Optional.of((User)principal) : Optional.empty();
    }
}
