package com.internetapplications.controller;

import com.internetapplications.InternetApplicationsMasterTestCase;
import com.internetapplications.entity.Car;
import com.internetapplications.entity.Parameter;
import com.internetapplications.mock.MockCar;
import com.internetapplications.mock.MockParameter;
import com.internetapplications.mock.MockUser;
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

public class ParameterControllerTest extends InternetApplicationsMasterTestCase {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @BeforeEach
    public void beforeEach() {
        this.userRepository.save(new MockUser().getInstance());
    }

    @Test
    public void create() throws Exception {
        Parameter parameter = new MockParameter().getInstance();

        mockMvc.perform(MockMvcRequestBuilders.post("/params/")
                .header("Authorization", "Bearer " + jwtService.generateToken(this.userRepository.findAll().get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(parameter))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void list() throws Exception {
        this.parameterRepository.saveAll(Arrays.asList(
                new MockParameter().getInstance(),
                new MockParameter().getInstance(),
                new MockParameter().getInstance(),
                new MockParameter().getInstance()
        ));


        mockMvc.perform(MockMvcRequestBuilders.get("/params/list")
                .header("Authorization", "Bearer " + jwtService.generateToken(this.userRepository.findAll().get(0)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    public void update() throws Exception {
        Parameter param = new MockParameter().getInstance();
        this.parameterRepository.save(param);

        String newParamValue = "New Value";

        param.setValue(newParamValue);

        mockMvc.perform(MockMvcRequestBuilders.put("/params/")
                .header("Authorization", "Bearer " + jwtService.generateToken(this.userRepository.findAll().get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(serialize(param))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertEquals(newParamValue, this.parameterRepository.findById(param.getId()).get().getValue());
    }

    @Test
    public void delete() throws Exception {
        Parameter param = new MockParameter().getInstance();
        this.parameterRepository.save(param);


        mockMvc.perform(MockMvcRequestBuilders.delete("/params/" + param.getId())
                .header("Authorization", "Bearer " + jwtService.generateToken(this.userRepository.findAll().get(0)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @AfterEach
    public void afterEach() {
        this.parameterRepository.deleteAll();
        this.userRepository.deleteAll();
    }
}
