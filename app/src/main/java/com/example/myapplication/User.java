package com.example.myapplication;

public class User {
    public String userName;
    public String userPassword;
    public String email;

    public User(){

    }

    public User(String username, String password, String email){
        this.userName = username;
        this.userPassword = password;
        this.email = email;
    }

}
