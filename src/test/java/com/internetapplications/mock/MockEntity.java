package com.internetapplications.mock;

import com.github.javafaker.Faker;
import com.internetapplications.entity.BaseEntity;

public interface MockEntity<T extends BaseEntity> {
    Faker faker = new Faker();
    T getInstance();
}
