package com.example.springboot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/getdata")
	public String getdata() {
		Connection connection = Application.getConnection();
		Statement stmt;
		StringBuilder builder = new StringBuilder();
		try {
			stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("select * from user_info");
			while (result.next()) {
				builder.append("EmailId: " + result.getString("emailid"));
				builder.append(" ");
				builder.append("Zipcode: " + result.getInt("zipcode"));
				builder.append(" ");
				builder.append(" <br> ");
			}
		} catch (SQLException e) {
			builder.append(e.toString());
		}
		return builder.toString();
	}

}
