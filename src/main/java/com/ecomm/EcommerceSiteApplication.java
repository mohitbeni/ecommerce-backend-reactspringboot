package com.ecomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
public class EcommerceSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceSiteApplication.class, args);
	}

}
