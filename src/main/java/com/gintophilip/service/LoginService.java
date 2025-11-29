package com.gintophilip.service;


import com.gintophilip.entities.ApplicationUser;
import com.gintophilip.repository.UserRepository;

public class LoginService {

    public ApplicationUser login(String username, String password) {
        ApplicationUser user = UserRepository.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
