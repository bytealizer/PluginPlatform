package com.gintophilip.repository;

import com.gintophilip.entities.ApplicationUser;
import com.gintophilip.entities.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private static final Map<String, ApplicationUser> users = new HashMap<>();

    static {
        initializeSampleData();
    }

    private static void initializeSampleData() {
        users.put("jdoe",      new ApplicationUser("John",  "Doe",     "jdoe",      "password123","NONE"));
        users.put("asmith",    new ApplicationUser("Alice", "Smith",   "asmith",    "alicePass","Hindi"));
        users.put("bjohnson",  new ApplicationUser("Bob",   "Johnson", "bjohnson",  "securePass","NONE"));
        users.put("ebrown",    new ApplicationUser("Emma",  "Brown",   "ebrown",    "hello123","Spanish"));
        users.put("lwilson",   new ApplicationUser("Liam",  "Wilson",  "lwilson",   "mypassword","NONE"));
    }
    public static ApplicationUser getUser(String username) {
        return users.get(username);
    }
    public static void addUser(ApplicationUser user) {
        users.put(user.getUserName(), user);
    }
    public static Map<String, ApplicationUser> getAllUsers() {
        return Collections.unmodifiableMap(users);
    }
}

