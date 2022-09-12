package com.example.springboot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {

    private ArrayList<Long> retrieveBooksAvailable(Long userid) {
        Connection connection = Application.getConnection();
        Statement stmt;

        ArrayList<Long> booksAvailableISBNs = new ArrayList<Long>();

        try {
            stmt = connection.createStatement();
            ResultSet result = stmt
                    .executeQuery("select * from books_available where userid=\"" + userid + "\" and active=1");
            while (result != null && result.next()) {
                Long isbn = result.getLong("isbn");
                booksAvailableISBNs.add(isbn);
            }

        } catch (SQLException e) {
            System.out.println("SQLException");
        }
        return booksAvailableISBNs;
    }

    private ArrayList<Long> retrieveBooksNeeded(Long userid) {
        Connection connection = Application.getConnection();
        Statement stmt;

        ArrayList<Long> booksNeededISBNs = new ArrayList<Long>();

        try {
            stmt = connection.createStatement();
            ResultSet result = stmt
                    .executeQuery("select * from books_needed where userid=\"" + userid + "\" and active=1");
            while (result != null && result.next()) {
                Long isbn = result.getLong("isbn");
                booksNeededISBNs.add(isbn);
            }

        } catch (SQLException e) {
            System.out.println("SQLException");
        }
        return booksNeededISBNs;
    }

    private ArrayList<Long> stringToArrayList(String isbns) {
        ArrayList<Long> booksForSaleISBNs = new ArrayList<Long>();
        if (isbns.isEmpty()) {
            return booksForSaleISBNs;
        }

        String str[] = isbns.split(",");

        List<String> booksForSale = new ArrayList<String>();
        booksForSale = Arrays.asList(str);

        for (String s : booksForSale) {
            booksForSaleISBNs.add(Long.parseLong(s));
        }
        return booksForSaleISBNs;
    }

    private String ALToString(ArrayList<Long> al) {

        String result = "";
        for (Long l : al) {
            String s = String.valueOf(l);
            result = result + s + ",";
        }
        return result;
    }

    // private void match(Long userid) {

    // ArrayList<Long> booksNeeded = retrieveBooksNeeded(userid);

    // // Find user's zipcode
    // long zipCode = findZip(userid);
    // // Find neighbors other than me
    // ArrayList<Long> neighborUserids = getNeighbors(userid, zipCode);

    // // See if any neighbors have what I need
    // for (Long neighbor: neighborUserids) {
    // ArrayList<Long> neighborHaveBooks = retrieveBooksAvailable(neighbor);

    // for (Long bookNeeded : booksNeeded) {
    // if (neighborHaveBooks.contains(bookNeeded)) {
    // // hey hey match found
    // }
    // }

    // }

    // Connection connection = Application.getConnection();
    // Statement stmt;

    // ArrayList<Long> booksForSaleISBNs = new ArrayList<Long>();

    // try {
    // stmt = connection.createStatement();
    // ResultSet result = stmt.executeQuery("select * from books_needed where
    // userid=\"" + userid + "\" and active=1");
    // while (result != null && result.next()) {
    // Long isbn = result.getLong("isbn");
    // booksForSaleISBNs.add(isbn);
    // }

    // } catch (SQLException e) {
    // System.out.println("SQLException");
    // }
    // return booksForSaleISBNs;

    // }

    // private void match(Long userid) {

    // }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/books")
    public String retrieveBooks(@RequestParam Long userid,
            @RequestParam String bwisbns,
            @RequestParam String bhisbns) {

        ArrayList<Long> booksWantedISBNs = stringToArrayList(bwisbns);
        ArrayList<Long> booksForSaleISBNs = stringToArrayList(bhisbns);

        Connection connection = Application.getConnection();
        Statement stmt;
        try {

            for (Long lBookWanted : booksWantedISBNs) {
                stmt = connection.createStatement();
                stmt.executeUpdate("insert into book_xchange.books_needed(userid, isbn) values (\"" + userid + "\","
                        + lBookWanted + ")");
            }

            for (Long lBookForSale : booksForSaleISBNs) {
                stmt = connection.createStatement();
                stmt.executeUpdate("insert into book_xchange.books_available(userid, isbn) values (\"" + userid + "\","
                        + lBookForSale + ")");
            }


            MatchWantsAndHaves matcher = new MatchWantsAndHaves(userid);
            List<UserISBNTuple> matchingTups = matcher.DoMatchingForMyWants();

            MatchWantsAndHaves matcher2 = new MatchWantsAndHaves(userid);
            List<UserISBNTuple> matchingTups2 = matcher2.DoMatchingForMyHaves();


            String returnNeedMatch = "</b> Here are the matches for books you require: </b><br>" ;
            for (UserISBNTuple t : matchingTups) {
                returnNeedMatch+= t.getisbn() + ":" + t.getEmailid() + "<br>";
            }

            String returnHaveMatch = "</b> Here are the matches for books you have: </b><br>";
            for (UserISBNTuple t : matchingTups2) {
                returnHaveMatch+= t.getisbn() + ":" + t.getEmailid() + "<br>";
           }
            String matches = returnNeedMatch + returnHaveMatch;
            return matches;

            // MatchWantsAndHaves matcher = new MatchWantsAndHaves(userid);
            // List<UserISBNTuple> matchingTups = matcher.DoMatchingForMyWants();
            // MatchWantsAndHaves matcher2 = new MatchWantsAndHaves(userid);
            // List<UserISBNTuple> matchingTups2 = matcher2.DoMatchingForMyHaves();

            // String returnNeedMatch = "";
            // String returnHaveMatch = ""; 
            // for (UserISBNTuple t : matchingTups) {
            //     returnNeedMatch+= t.getisbn() + ":" + t.getEmailid() + "\n";
            // }
            // return returnNeedMatch;

            // for (UserISBNTuple t : matchingTups2) {
            //     returnHaveMatch+= t.getisbn() + ":" + t.getEmailid() + "\n";
            // }
            // return returnHaveMatch;
            // String booksAvailable = ALToString(booksWantedISBNs);
            // String booksForSale = ALToString(booksForSaleISBNs);

            // BookInfo bookInfo = new BookInfo(booksAvailable, booksForSale, userid);
            // return bookInfo;

        } catch (SQLException e) {
            System.out.println("SQLException");
        }
        return null;
    }
}
