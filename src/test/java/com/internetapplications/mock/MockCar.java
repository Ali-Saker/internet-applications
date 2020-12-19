package com.internetapplications.mock;

import com.internetapplications.entity.Car;

public class MockCar implements MockEntity<Car> {

    @Override
    public Car getInstance() {
        Car car = new Car();
        car.setName(faker.name().name());
        car.setPrice(faker.number().randomDouble(2, 100, 500));
        car.setSeatsNumber(faker.number().randomDigit() + 1);
        return car;
    }
}
