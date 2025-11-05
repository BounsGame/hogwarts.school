package ru.hogwarts.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class SchoolApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SchoolApplication.class);

		app.setDefaultProperties(Collections.singletonMap(
				"spring.profiles.active", "dev"
		));
		app.run(args);
	}

}
