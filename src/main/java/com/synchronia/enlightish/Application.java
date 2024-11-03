package com.synchronia.enlightish;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_URL", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_URL"));
		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_USERNAME", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_USERNAME"));
		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_PASSWORD", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_PASSWORD"));

		SpringApplication.run(Application.class, args);
	}

}
