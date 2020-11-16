package com.internetapplications.mock;

import com.internetapplications.entity.User;

public class MockUser extends MockEntity {

    public static User getInstance() {
        User user = new User();
        user.setFirstName(faker.firstName());
        user.setLastName(faker.lastName());
        user.setEmail(faker.name()+"@test.com");
        user.setPassword("RandomPassword");
        return user;
    }
}
