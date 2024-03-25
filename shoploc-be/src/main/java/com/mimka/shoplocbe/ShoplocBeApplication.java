package com.mimka.shoplocbe;

import com.mimka.shoplocbe.bi.BiCustomerConnectionComponent;
import com.mimka.shoplocbe.bi.BiGiftComponent;
import com.mimka.shoplocbe.bi.BiOrderComponent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShoplocBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoplocBeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (BiCustomerConnectionComponent biCustomerConnectionComponent,
										 BiOrderComponent biOrderComponent,
										 BiGiftComponent biGiftComponent) {
		return args -> {

			// Customer connections
			//biCustomerConnectionComponent.process();

			// Customer Orders
			//biOrderComponent.process();

			// Customer gift
			biGiftComponent.process();
		};
	}
}
