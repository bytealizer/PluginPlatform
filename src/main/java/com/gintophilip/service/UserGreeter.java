package com.gintophilip.service;

import com.gintophilip.entities.ApplicationUser;
import com.gintophilip.entities.User;
import com.gintophilip.repository.PluginRepository;

public class UserGreeter {

    public void greetUser(ApplicationUser user){
        PluginRepository.get(user.getPreferredLanguage()).greet(user.getUserName());
    }
}
