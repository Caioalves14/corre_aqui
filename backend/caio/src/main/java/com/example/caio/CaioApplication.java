package com.example.caio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.caio.repository")
public class CaioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaioApplication.class, args);
	}

}
