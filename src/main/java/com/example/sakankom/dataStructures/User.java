package com.example.sakankom.dataStructures;

public class User {
    private String username;
    private String password;
    private String userType;
    private boolean flag;

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public User() {
    }

    public User(String username, String password, String userType, boolean flag) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.flag = false;
    }

    // Getters -------------------------------------------------------------
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
    // Setters -------------------------------------------------------------
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
