package com.example.springboot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	private static Connection connection;

	public static Connection getConnection() {
		return connection;
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_xchange",
					"root",
					"sahanaduckjaadi02");

			System.out.println("!!!!!!!!!!! MYSQL DRIVER LOADED !!!!!!!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
