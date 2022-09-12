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


	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/user")
	public UserInfo getUser(@RequestParam String emailID, @RequestParam Long zipCode) {
		System.out.println("emailID= " + emailID);
        System.out.println("zipCode= " + zipCode);
        Connection connection = Application.getConnection();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery("select * from user_info where emailid=\"" + emailID + "\"");
			while (result != null && result.next()) {
                String localEmail = result.getString("emailid"); 
                if (localEmail.equalsIgnoreCase(emailID)) {
                    Long localZIP = result.getLong("zipcode"); 
                    Long userID = result.getLong("userid");
                   // String userID = result.getString("userid"); 
                    System.out.println("Profile exists for user with email:" + emailID + ", zipCode=" + localZIP);
                    return new UserInfo (emailID, localZIP, userID); 
                }
			}

            //creating a userid/profile
            stmt.executeUpdate("insert into book_xchange.user_info(emailid, zipcode) values (\"" + emailID + "\","+ zipCode + ")");

            Statement queryStmt = connection.createStatement();

            // Retrieve the new userId
            ResultSet resultQuery = queryStmt.executeQuery("select userid from user_info where emailid=\"" + emailID + "\"");
            while (resultQuery != null && resultQuery.next()) {
                Long newUserID = resultQuery.getLong("userid"); 
                return new UserInfo (emailID, zipCode, newUserID);
            }

		} catch (SQLException e) {
            System.out.println("SQLException");
		}
		return null;
	}

}
