package com.gintophilip;

import com.gintophilip.entities.ApplicationUser;
import com.gintophilip.entities.User;
import com.gintophilip.repository.PluginRepository;
import com.gintophilip.repository.UserRepository;
import com.gintophilip.service.LoginService;
import com.gintophilip.service.UserGreeter;

import java.io.Console;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserRepository userRepository = new UserRepository();
    private static final PluginRepository pluginRepository = new PluginRepository();

    public static void main(String[] args) {
        loadPlugins();
        startCommandLoop();
    }

    private static void loadPlugins() {
        pluginRepository.loadPlugins();
    }

    private static void startCommandLoop() {
        ApplicationUser currentUser = null;
        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "q!":
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                case "D":

                    if(currentUser!=null){
                        System.out.println("USer Details");
                        System.out.println("First Name: "+currentUser.getFirstName());
                        System.out.println("Last Name: "+currentUser.getLastName());
                        System.out.println("User Name: "+currentUser.getUserName());
                        System.out.println("Preferred Language: "+currentUser.getPreferredLanguage());
                    }else {
                        System.out.println("Please login first");
                    }
                    break;

                case "L":
                    UserCredentials credentials = collectCredentials();
                    ApplicationUser user = new LoginService().login(credentials.getUsername(), credentials.getPassword());
                    if (user != null) {
                        currentUser = user;
                        new UserGreeter().greetUser(user);
                    }else{
                        System.out.println("USer does not exist. check username and password is correct");
                    }
                    break;

                default:
                    System.out.println("Unknown command. Try 'L' to login or 'q!' to quit.");
            }
        }
    }


    public static UserCredentials collectCredentials() {
        Console console = System.console();

        if (console == null) {
            return defaultCollectCredentials();
        }
        String userName = console.readLine("Enter UserName:");
        char[] passwordChars = console.readPassword("Enter Password:");
        String password = new String(passwordChars);
        return new UserCredentials(userName, password);
    }

    private static UserCredentials defaultCollectCredentials() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        return new UserCredentials(userName, password);
    }
}
