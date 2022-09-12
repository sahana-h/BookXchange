package com.example.springboot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

	private static final String template = "Hello, %s!";

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/user")
	public UserInfo getUser(@RequestParam String emailID) {
		System.out.println("emailID= " + emailID);
        Connection connection = Application.getConnection();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("select * from user_info where emailid=\"" + emailID + "\"");
			while (result != null && result.next()) {
                String localEmail = result.getString("emailid"); 
                if (localEmail.equalsIgnoreCase(emailID)) {
                    Long localZIP = result.getLong("zipcode"); 
                   // String userID = result.getString("userid"); 
                    System.out.println("found email" + emailID);
                    return new UserInfo (emailID, localZIP); 
                }
			}
            System.out.println("did not find email" + emailID);
		} catch (SQLException e) {
            System.out.println("SQLException");
		}
		return null;
	}

}
