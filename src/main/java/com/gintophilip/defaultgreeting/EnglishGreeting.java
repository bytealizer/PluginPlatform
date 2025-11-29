package com.gintophilip.defaultgreeting;

import com.gintophilip.core.greeting.contract.GreetingPlugin;

public class EnglishGreeting implements GreetingPlugin {

    @Override
    public void greet(String userName) {
        System.out.println("Hello "+ userName+" "+"Welocme");
    }

    @Override
    public String getLanguage() {
        return "English";
    }
}
