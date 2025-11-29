package com.gintophilip.entities;

public class ApplicationUser {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String preferredLanguage;

    public ApplicationUser(String firstName, String lastName, String userName, String password,String preferredLanguage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.preferredLanguage = preferredLanguage.equals("NONE")?"English":preferredLanguage;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public String getPassword() {
        return password;
    }


}
