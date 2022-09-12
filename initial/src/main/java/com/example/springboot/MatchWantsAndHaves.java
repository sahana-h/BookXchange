package com.example.springboot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Matches a users wants to other users have.
public class MatchWantsAndHaves {
    private int userid;

    public MatchWantsAndHaves(int userid) {
        this.userid = userid;
    }

    public void DoMatching() {
        // Connect to Database
        Set<Integer> usersWant = getUsersWant();
        List<Integer> userIds = getUsersWhoHaveBooks(usersWant);
        for (Integer id : userIds) {
            System.out.println(id);
        }
    }

    private Set<Integer> getUsersWant() {
        Connection connection = Application.getConnection();
        Set<Integer> wantIsbns = new HashSet<Integer>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(
                    "select * from books_needed where userid=" + userid + " AND active= 1");
            while (result.next()) {
                wantIsbns.add(result.getInt("isbn"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return wantIsbns;
    }

    private List<Integer> getUsersWhoHaveBooks(Set<Integer> isbns) {
        Connection connection = Application.getConnection();
        List<Integer> userIds = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(
                    "select * from books_available where active= 1");
            while (result.next()) {
                Integer isbn = result.getInt("userid");
                userIds.add(isbn);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return userIds;
    }

}
