package com.internetapplications.mock;

import com.internetapplications.entity.Parameter;

public class MockParameter implements MockEntity<Parameter> {

    @Override
    public Parameter getInstance() {
        Parameter parameter = new Parameter();
        parameter.setName(faker.name().name());
        parameter.setValue(faker.name().name());
        return parameter;
    }
}
