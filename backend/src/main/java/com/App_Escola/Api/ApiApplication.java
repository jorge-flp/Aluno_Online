package com.App_Escola.Api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		System.out.println("========== ENV TEST ==========");
		System.out.println("DB_URL: " + (dotenv.get("DB_URL") != null));
		System.out.println("DB_USERNAME: " + (dotenv.get("DB_USERNAME") != null));
		System.out.println("DB_PASSWORD: " + (dotenv.get("DB_PASSWORD") != null));
		System.out.println("==============================");

		try {
			Connection connection = DriverManager.getConnection(
					dotenv.get("DB_URL"),
					dotenv.get("DB_USERNAME"),
					dotenv.get("DB_PASSWORD"));

			System.out.println("PostgreSQL conectado com sucesso!");

			connection.close();

		} catch (Exception e) {
			System.out.println("Erro na conexão PostgreSQL: " + e.getMessage());
		}

		SpringApplication.run(ApiApplication.class, args);
	}
}