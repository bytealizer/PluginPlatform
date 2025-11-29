package com.gintophilip.entities;

public class User {
    private int id;
    private String userName;
    private String language;

    public User(int id,String userName, String language) {
        this.userName = userName;
        this.language = language;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getLanguage() {
        return language;
    }

    public int getId() {
        return id;
    }
}
