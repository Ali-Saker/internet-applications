package com.internetapplications.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties
public class InternetApplicationsConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "internetapplications")
    public InternetApplicationsProperties internetApplicationsProperties() {
        return new InternetApplicationsProperties();
    }
}
