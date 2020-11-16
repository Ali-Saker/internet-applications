package com.internetapplications.security;

import com.internetapplications.entity.User;
import com.internetapplications.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional(
        readOnly = true
)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    UserDetailsServiceImpl() {
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = this.userRepository.findByEmail(email);
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Unauthorized");
        }
    }
}
