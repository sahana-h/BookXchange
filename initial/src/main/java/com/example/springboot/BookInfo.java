package com.example.springboot;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BookInfo {

    private String booksAvailable;
    private String booksForSale;
    private Long userid;

    public BookInfo(String booksAvailable,
     String booksForSale,
     Long userid) {
        this.booksAvailable = booksAvailable;
        this.booksForSale = booksForSale;
        this.userid = userid;
    }

    public String getBooksAvailable(){
        return booksAvailable; 
    }

    public String getBooksForSale(){
        return booksForSale; 
    }

    public Long getuserid(){
        return userid; 
    }



    
    
}
