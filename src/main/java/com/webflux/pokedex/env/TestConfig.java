package com.webflux.pokedex.env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private TestService testService;

    @Bean
    public void instantiateDataBase() {
        testService.init();
    }
}
