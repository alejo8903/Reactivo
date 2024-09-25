package com.tallerfinal.tallerfinal;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class TallerfinalApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("MONGO_URI", Objects.requireNonNull(dotenv.get("MONGO_URI")));

		SpringApplication.run(TallerfinalApplication.class, args);
	}

}
