package com.example.dishdiary.data.model.authDTO;

public class AuthenticationPoJo {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String userName;
    private String userEmail;
    private String password;

    public AuthenticationPoJo(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
    }


    public AuthenticationPoJo() {
    }

    public AuthenticationPoJo(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }


}
