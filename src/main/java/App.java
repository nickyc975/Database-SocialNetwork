import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import entities.*;

public class App {
    private static User user;
    private static String operation, table;
    private static Map<String, String> arguments = new HashMap<>();

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/social_network?user=root&password=123456");
            Entity.setConnection(connection);

            Scanner scanner = new Scanner(System.in);
            parse(scanner.nextLine());
            while (!operation.equals("exit")) {
                if (user != null && user.isAuthenticated()) {
                    print(user.getUsername() + "> ");
                } else {
                    print("> ");
                }
                parse(scanner.nextLine());
            }
            scanner.close();
            connection.close();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void parse(String str) {
        arguments.clear();
        String[] parts = str.split(" ", 2);
        operation = parts[0];
        switch (operation) {
            case "register":
                handleRegister(parts[1]);
                break;
            case "login":
                handleLogin(parts[1]);
                break;
            case "show":
                break;
            case "add":
                break;
            case "update":
                break;
            case "remove":
                break;
            case "save":
                break;
            case "logout":
                handleLogout();
                break;
            case "unregister":
                handleUnregister();
                break;
            case "exit":
                break;
            default:
                break;
        }
    }

    private static void handleRegister(String str) {
        if (user != null && user.isAuthenticated()) {
            println("User " + user.getUsername() + " has logged in!");
            return;
        }
    
        String[] parts = str.split(" ", 2);
        user = new User(parts[0], parts[1]);
        try {
            user.create();
            println("Welcome " + user.getUsername() + "!");
            return;
        } catch (SQLException e) {
            println(e.getMessage());
        }
    }

    private static void handleLogin(String str) {
        if (user != null && user.isAuthenticated()) {
            println("User " + user.getUsername() + " has logged in!");
            return;
        }
        
        String[] parts = str.split(" ", 2);
        user = new User(parts[0], parts[1]);
        try {
            if (user.login()) {
                user.load();
                println("Welcome " + user.getName() + "!");
            } else {
                println("Wrong username or password!");
            }
            return;
        } catch (SQLException e) {
            println(e.getMessage());
        }
    }

    private static void handleLogout() {
        if (user == null || !user.isAuthenticated()) {
            println("User not logged in!");
            return;
        }
        
        user = null;
    }

    private static void handleUnregister() {
        if (user == null || !user.isAuthenticated()) {
            println("User not logged in!");
            return;
        }
    
        try {
            user.delete();
            user = null;
        } catch (SQLException e) {
            println(e.getMessage());
        }
    }

    private static void print(String str) {
        System.out.print(str);
    }

    private static void println(String str) {
        System.out.println(str);
    }
}
