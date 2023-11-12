package com.mimka.shoplocbe;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class ShoplocBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoplocBeApplication.class, args);
	}


}
