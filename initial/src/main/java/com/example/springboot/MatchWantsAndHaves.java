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
    private long zipc;

    public MatchWantsAndHaves(long userid) {
        this.userid = userid;
    }

    public List<UserISBNTuple> DoMatchingForMyWants() {
        // Connect to Database
        Set<Long> usersWant = getUsersWant();
        // List<Long> userIds = getUsersWhoHaveBooks(usersWant);
        List<UserISBNTuple> matchingUsers = getUsersWhoHaveBooks(usersWant);


        // ArrayList<UserISBNTuple> matchingUsers = new ArrayList<UserISBNTuple>();

        // for (Long id : userIds) {
        //     System.out.println("UserId:" + id + " has the book I need");

        //     UserISBNTuple tup = new UserISBNTuple(id, );
        //     matchingUsers.add(id);
        // }

        return matchingUsers;
    }
    //NEW CODE -  CHECK FOR ERRORS
    public List<UserISBNTuple> DoMatchingForMyHaves() {
        // Connect to Database
        Set<Long> usersWant = getUsersHave();
        // List<Long> userIds = getUsersWhoHaveBooks(usersWant);
        List<UserISBNTuple> matching = getUsersWhoWantBooks(usersWant);

        return matching;
    }

    private Set<Long> getUsersWant() {
        Connection connection = Application.getConnection();
        Set<Long> wantIsbns = new HashSet<Long>();
        Long zipUser = getUserZip();
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

    //NEW CODE - CHECK FOR ERRORS
    private Set<Long> getUsersHave() {
        Connection connection = Application.getConnection();
        Set<Long> haveISBNs = new HashSet<Long>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(
                    "select * from books_available where userid=" + userid + " AND active= 1");
            while (result.next()) {
                haveISBNs.add(result.getLong("isbn"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return haveISBNs;
    }

    private Long getUserZip() {
        Connection connection = Application.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(
                    "select * zipcode from user_info where userid<>" + userid);
            while (result.next()) {
                zipc = result.getLong("zipcode");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return zipc;
    }

    // private List<UserISBNTuple> getUsersWhoHaveBooks(Set<Long> isbns) {
    //     Connection connection = Application.getConnection();
    //     // List<Long> userIds = new ArrayList<>();

    //     List<UserISBNTuple> tup = new ArrayList<UserISBNTuple>();

    //     try {
    //         Statement stmt = connection.createStatement();
    //         ResultSet result = stmt.executeQuery(
    //                 "select * from books_available where active= 1 and userid<>" + userid);
    //         while (result != null && result.next()) {
    //             Long neighboruserid = result.getLong("userid");
    //             Long isbn = result.getLong("isbn");
    //             if (isbns.contains(isbn)) {

    //                 // Someone has the book that I need
    //                 // Check if this person has my same zip code
    //                 String emailid = getEmail(neighboruserid);

    //                 UserISBNTuple t = new UserISBNTuple(neighboruserid, emailid, isbn);
    //                 tup.add(t);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         System.out.println(e);
    //     }
    //     return tup;
    // }

    private List<UserISBNTuple> getUsersWhoHaveBooks(Set<Long> isbns) {
        Connection connection = Application.getConnection();
        // List<Long> userIds = new ArrayList<>();

        List<UserISBNTuple> tup = new ArrayList<UserISBNTuple>();
	Long userzip = getUserZip(); 

        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(
                "select * from books_available where active= 1 and userid<>" + userid);
              
            while (result != null && result.next()) {
                Long neighboruserid = result.getLong("userid");
                Long isbn = result.getLong("isbn");
                if (isbns.contains(isbn)) {

                    // Someone has the book that I need
                    String emailid = getEmail(neighboruserid);

                    UserISBNTuple t = new UserISBNTuple(neighboruserid, emailid, isbn);
                    tup.add(t);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return tup;
    }

    //NEW CODE- CHECK FOR ERRORS
    private List<UserISBNTuple> getUsersWhoWantBooks(Set<Long> isbns) {
        Connection connection = Application.getConnection();
        // List<Long> userIds = new ArrayList<>();

        List<UserISBNTuple> tup = new ArrayList<UserISBNTuple>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(
                    "select * from books_needed where active= 1 and userid<>" + userid);
            while (result != null && result.next()) {
                Long neighboruserid = result.getLong("userid");
                Long isbn = result.getLong("isbn");
                if (isbns.contains(isbn)) {

                    // Someone needs the book that I have
                    // Check if this person has my same zip code
                    String emailid = getEmail(neighboruserid);

                    UserISBNTuple t = new UserISBNTuple(neighboruserid, emailid, isbn);
                    tup.add(t);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return tup;
    }

    String getEmail(Long userid) {
        Connection connection = Application.getConnection();
        Statement stmt;

        try {
            stmt = connection.createStatement();
            ResultSet result = stmt
                    .executeQuery("select emailid from user_info where userid=" + userid);
            while (result != null && result.next()) {
                String emailid = result.getString("emailid");
                return emailid;
            }

        } catch (SQLException e) {
            System.out.println("SQLException");
        }
        return null;
    }

}
