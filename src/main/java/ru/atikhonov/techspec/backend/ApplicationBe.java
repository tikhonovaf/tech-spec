package ru.atikhonov.techspec.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition
@ComponentScan(basePackages = {"ru.atikhonov"})
public class ApplicationBe {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationBe.class, args);
	}

}
