package com.internetapplications;

import com.internetapplications.entity.User;
import com.internetapplications.mock.MockUser;
import com.internetapplications.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest extends InternetApplicationsApplicationTests {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void sendNotification() {
        User user = MockUser.getInstance();
        userRepository.save(user);
    }
}
