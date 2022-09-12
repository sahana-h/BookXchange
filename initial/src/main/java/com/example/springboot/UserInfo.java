package com.example.springboot;
public class UserInfo {
    private final String emailID; 
    private final long zipcode;
    private final long userid; 

    public UserInfo(String emailID, long zipcode, long userid){
        this.emailID = emailID;
        this.zipcode = zipcode; 
        this.userid = userid;
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



}
