package com.internetapplications.controller;

import com.internetapplications.InternetApplicationsMasterTestCase;
import com.internetapplications.entity.Car;
import com.internetapplications.entity.Parameter;
import com.internetapplications.mock.MockCar;
import com.internetapplications.mock.MockUser;
import com.internetapplications.repository.CarRepository;
import com.internetapplications.repository.ParameterRepository;
import com.internetapplications.repository.UserRepository;
import com.internetapplications.security.JwtService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarControllerTest extends InternetApplicationsMasterTestCase {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private JwtService jwtService;

    @BeforeEach
    public void beforeEach() {
        this.userRepository.save(new MockUser().getInstance());
    }

    @Test
    public void create() throws Exception {
        Car car = new MockCar().getInstance();

        Parameter parameter = new Parameter();
        parameter.setName("Profit Rate");
        parameter.setValue("50");
        this.parameterRepository.save(parameter);


        mockMvc.perform(MockMvcRequestBuilders.post("/cars/")
                .header("Authorization", "Bearer " + jwtService.generateToken(this.userRepository.findAll().get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(car))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void list() throws Exception {
        this.carRepository.saveAll(Arrays.asList(
                new MockCar().getInstance(),
                new MockCar().getInstance(),
                new MockCar().getInstance(),
                new MockCar().getInstance()
        ));


        mockMvc.perform(MockMvcRequestBuilders.get("/cars/list")
                .header("Authorization", "Bearer " + jwtService.generateToken(this.userRepository.findAll().get(0)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    public void update() throws Exception {
        Car car = new MockCar().getInstance();
        this.carRepository.save(car);

        String newParamValue = "New Value";

        car.setName(newParamValue);

        mockMvc.perform(MockMvcRequestBuilders.put("/cars/")
                .header("Authorization", "Bearer " + jwtService.generateToken(this.userRepository.findAll().get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(car))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertEquals(newParamValue, this.carRepository.findById(car.getId()).get().getName());
    }

    @Test
    public void delete() throws Exception {
        Car car = new MockCar().getInstance();
        this.carRepository.save(car);


        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/" + car.getId())
                .header("Authorization", "Bearer " + jwtService.generateToken(this.userRepository.findAll().get(0)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @AfterEach
    public void afterEach() {
        this.carRepository.deleteAll();
        this.userRepository.deleteAll();
        this.parameterRepository.deleteAll();
    }
}
