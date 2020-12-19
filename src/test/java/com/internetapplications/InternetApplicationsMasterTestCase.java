package com.internetapplications;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {InternetApplicationsApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class InternetApplicationsMasterTestCase {

    @Autowired
    protected MockMvc mockMvc;

    protected String serialize(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(MapperFeature.USE_ANNOTATIONS);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
