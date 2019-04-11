package com.benxiaopao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.benxiaopao.provider.dao.map")
public class RestaurantsWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantsWebsiteApplication.class, args);
    }

}
