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
        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "q!":
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                case "D":
                    System.out.println("USer Details");
                    break;

                case "L":
                    UserCredentials credentials = collectCredentials();
                    ApplicationUser user = new LoginService().login(credentials.getUsername(), credentials.getPassword());
                    if (user != null) {
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
