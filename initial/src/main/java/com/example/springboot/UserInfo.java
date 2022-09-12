package com.example.springboot;
public class UserInfo {
    private final String emailID; 
    private final long zipcode;
    private final long userid; 
    private boolean newUser;

    public UserInfo(String emailID, long zipcode, long userid){
        this.emailID = emailID;
        this.zipcode = zipcode; 
        this.userid = userid;
        this.newUser = false;
    }

    public UserInfo(String emailID, long zipcode, long userid, boolean newUser){
        this.emailID = emailID;
        this.zipcode = zipcode; 
        this.userid = userid;
        this.newUser = newUser;// true
    }

    public String getemailID(){
        return emailID; 
    }

    public long getzipcode(){
        return zipcode; 
    }

    public long getuserid(){
        return userid; 
    }

    public boolean getNewUser() {
        return newUser;
    }


}
