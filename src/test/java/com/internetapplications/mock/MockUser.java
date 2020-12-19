package com.internetapplications.mock;

import com.internetapplications.entity.User;

public class MockUser implements MockEntity<User> {

    @Override
    public User getInstance() {
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.name() + "@test.com");
        user.setPassword("RandomPassword");
        return user;
    }
}
