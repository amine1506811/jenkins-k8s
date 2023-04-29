package com.veterinary.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.veterinary"})
@EnableJpaRepositories(basePackages = {"com.veterinary.repository"})
public class VeterinaryMsConfiguration {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

