package com.minse0.kbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing 
@SpringBootApplication
public class KbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(KbcApplication.class, args);
	}

}
