package com.shoppingcart.app.demoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("com.shoppingcart.app.repository")
@EntityScan("com.shoppingcart.app.entity")
@ComponentScan({ "com.shoppingcart.app.config","com.shoppingcart.app.controller", "com.shoppingcart.app.service", "com.shoppingcart.app.service.impl"})
public class CartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }

}

