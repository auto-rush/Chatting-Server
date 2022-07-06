package com.autorush.rushchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RushchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(RushchatApplication.class, args);
	}

}
