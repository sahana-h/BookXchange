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
    private long userid;

    public MatchWantsAndHaves(long userid) {
        this.userid = userid;
    }

    public void DoMatching() {
        // Connect to Database
        Set<Long> usersWant = getUsersWant();
        List<Long> userIds = getUsersWhoHaveBooks(usersWant);
        for (Long id : userIds) {
            System.out.println("UserId:" + id + " has the book I need");
        }
    }

    private Set<Long> getUsersWant() {
        Connection connection = Application.getConnection();
        Set<Long> wantIsbns = new HashSet<Long>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(
                    "select * from books_needed where userid=" + userid + " AND active= 1");
            while (result.next()) {
                wantIsbns.add(result.getLong("isbn"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return wantIsbns;
    }

    private List<Long> getUsersWhoHaveBooks(Set<Long> isbns) {
        Connection connection = Application.getConnection();
        List<Long> userIds = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(
                    "select * from books_available where active= 1");
            while (result.next()) {
                Long isbn = result.getLong("userid");
                if (isbns.contains(isbn)) {
                    userIds.add(isbn);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return userIds;
    }

}
