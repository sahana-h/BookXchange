package com.example.springboot;

public class UserISBNTuple {

    private Long userid;
    private String emailid;
    private Long isbn;

    public UserISBNTuple(long userid, String emailid, long isbn) {
        this.userid = userid;
        this.emailid = emailid;
        this.isbn = isbn;
    }

    public Long getUserid() {
        return userid;
    }

    public String getEmailid() {
        return emailid;
    }

    public Long getisbn() {
        return isbn;
    }

}