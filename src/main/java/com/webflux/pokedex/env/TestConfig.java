package com.webflux.pokedex.env;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@AllArgsConstructor
public class TestConfig {

    private final TestService testService;

    @Bean
    public void instantiateDataBase() {
        testService.init();
    }
}
