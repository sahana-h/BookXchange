package com.example.springboot;
public class UserInfo {
    private final String emailID; 
    private final long zipcode;

    public UserInfo(String emailID, long zipcode){
        this.emailID = emailID;
        this.zipcode = zipcode; 
    }

    public String getemailID(){
        return emailID; 
    }

    public long getzipcode(){
        return zipcode; 
    }

    

}
