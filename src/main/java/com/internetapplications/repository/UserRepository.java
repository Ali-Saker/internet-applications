package com.internetapplications.repository;

import com.internetapplications.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {
    User findByEmail(String email);
}
