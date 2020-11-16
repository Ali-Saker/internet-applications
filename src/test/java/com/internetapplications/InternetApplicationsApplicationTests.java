package com.internetapplications;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {InternetApplicationsApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
class InternetApplicationsApplicationTests {

    @Test
    void contextLoads() {
    }

}
