package com.example.websellingdrugs.data.viewmodels;

import jakarta.validation.constraints.NotEmpty;

public class user {

    
    @NotEmpty(message = "The name is required")
    private String Email;
    public user() {
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    @NotEmpty(message = "The name is required")
    private String Password;
    
}
