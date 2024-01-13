package com.example.repairershubofficial;

public class Users {

    public String fullname, email;

    public Users(){

    }
    public Users(String fullname, String email)
    {
        this.fullname = fullname;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }
}
