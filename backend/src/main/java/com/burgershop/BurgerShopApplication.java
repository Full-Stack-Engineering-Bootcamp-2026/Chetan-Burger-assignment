package com.burgershop;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BurgerShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BurgerShopApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        System.out.println("model mapper");
        return new ModelMapper();
    }

}
