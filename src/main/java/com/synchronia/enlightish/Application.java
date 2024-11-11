package com.synchronia.enlightish;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_URL_DEV", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_URL_DEV"));
		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_USERNAME_DEV", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_USERNAME_DEV"));
		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_PASSWORD_DEV", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_PASSWORD_DEV"));

		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_URL_PROD", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_URL_PROD"));
		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_USERNAME_PROD", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_USERNAME_PROD"));
		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_PASSWORD_PROD", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_PASSWORD_PROD"));

		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_URL_TEST", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_URL_TEST"));
		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_USERNAME_TEST", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_USERNAME_TEST"));
		System.setProperty("SPRING_DATASOURCE_ENLIGHTISH_PASSWORD_TEST", dotenv.get("SPRING_DATASOURCE_ENLIGHTISH_PASSWORD_TEST"));

		SpringApplication.run(Application.class, args);
	}

}
