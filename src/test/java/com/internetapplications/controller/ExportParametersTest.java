package com.internetapplications.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internetapplications.InternetApplicationsMasterTestCase;
import com.internetapplications.entity.Parameter;
import com.internetapplications.helper.CsvHelper;
import com.internetapplications.repository.ParameterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExportParametersTest extends InternetApplicationsMasterTestCase {

    @Value("classpath:MOCK_DATA.json")
    Resource resourceFile;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private CsvHelper csvHelper;

    @BeforeEach
    public void insertMockData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Parameter>> typeReference = new TypeReference<List<Parameter>>(){};
        List<Parameter> parameters = mapper.readValue(resourceFile.getInputStream(), typeReference);
        this.parameterRepository.saveAll(parameters);
    }

    @Test
    public void exportParameters() throws IOException {

        csvHelper.generateCsv(this.parameterRepository.findAll(), Parameter.class);

    }

    @AfterEach
    public void afterEach() {
        this.parameterRepository.deleteAll();
    }
}
