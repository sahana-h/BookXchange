package com.example.springboot;

public class BooksAvailable {
    private String userid;
    private int isbn;
    private boolean isActive;

    public BooksAvailable(String userid, int isbn, boolean isActive) {
        this.userid = userid;
        this.isbn = isbn;
        this.isActive = isActive;
    }

    public String getUserid() {
        return userid;
    }

    public int getIsbn() {
        return isbn;
    }

    public boolean isActive() {
        return isActive;
    }

}
