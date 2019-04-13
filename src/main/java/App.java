import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import database.Model;
import database.entities.User;

public class App {
    private static User user;
    private static String operation, model, identity;
    private static List<Arg> args = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/social_network?user=root&password=123456");
            Model.setConnection(connection);

            Scanner scanner = new Scanner(System.in);
            
            print("> ");
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
        args.clear();
        model = null;
        identity = null;

        String[] parts = str.split(" +", 2);
        operation = parts[0];
        switch (operation) {
            case "register":
                handleRegister(parts[1]);
                break;
            case "login":
                handleLogin(parts[1]);
                break;
            case "show":
                parseView(parts[1]);
                break;
            case "add":
                parseEntity(parts[1]);
                break;
            case "update":
                parseEntity(parts[1]);
                break;
            case "remove":
                parseEntity(parts[1]);
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

    private static void parseView(String str) {
        if (user == null || !user.isAuthenticated()) {
            println("User not logged in!");
            return;
        }

        String[] parts = str.split(" +", 2);
        model = parts[0];
        if (parts.length > 1) {
            parseArgs(parts[1]);
        }
        switch (model) {
            case "info":
                println(user.toString());
                break;
            case "education":
                ViewHandlers.handleEducationView(identity, args);
                break;
            case "work":
                ViewHandlers.handleWorkView(identity, args);
                break;
            case "user":
                ViewHandlers.handleUserView(identity, args);
                break;
            case "friend":
                ViewHandlers.handleFriendView(identity, args);
                break;
            case "friend_group":
                ViewHandlers.handleFriendGroupView(identity, args);
                break;
            case "post":
                ViewHandlers.handlePostView(identity, args);
                break;
            case "reply":
                ViewHandlers.handleReplyView(identity, args);
                break;
            case "share":
                ViewHandlers.handleShareView(identity, args);
                break;
            default:
                break;
        }
    }

    private static void parseEntity(String str) {
        if (user == null || !user.isAuthenticated()) {
            println("User not logged in!");
            return;
        }

        String[] parts = str.split(" +", 2);
        model = parts[0];
        if (parts.length > 1) {
            parseArgs(parts[1]);
        }
        switch (model) {
            case "info":
                EntityHandlers.handleInfo(operation, identity, args);
                break;
            case "education":
                EntityHandlers.handleEducationEntity(operation, identity, args);
                break;
            case "work":
                EntityHandlers.handleWorkEntity(operation, identity, args);
                break;
            case "friend":
                EntityHandlers.handleFriendEntity(operation, identity, args);
                break;
            case "friend_group":
                EntityHandlers.handleFriendGroupEntity(operation, identity, args);
                break;
            case "post":
                EntityHandlers.handlePostEntity(operation, identity, args);
                break;
            case "reply":
                EntityHandlers.handleReplyEntity(operation, identity, args);
                break;
            case "share":
                EntityHandlers.handleShareEntity(operation, identity, args);
                break;
            default:
                break;
        }
    }

    private static void parseArgs(String str) {
        Arg arg;
        String argsStr = str.trim();
        if (!argsStr.startsWith("(")) {
            String[] parts = argsStr.split(" +", 2);
            identity = parts[0];
            if (parts.length == 1) {
                return;
            }
            argsStr = parts[1];
        }

        argsStr = argsStr.trim();
        if (!argsStr.startsWith("(") || !argsStr.endsWith(")")) {
            return;
        }
        argsStr = argsStr.substring(1, argsStr.length() - 1);
        if (!argsStr.startsWith("(") || !argsStr.endsWith(")")) {
            return;
        }
        argsStr = argsStr.substring(1, argsStr.length() - 1);
        List<String> argsList = Arrays.asList(argsStr.split("\\) *, *\\("));
        for (String s : argsList) {
            s = s.trim();
            if (s.length() > 0) {
                String[] parts = s.split(" *, *", 3);
                if (parts.length == 3) {
                    arg = new Arg(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    // println(arg.toString());
                    args.add(arg);
                }
            }
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
            ViewHandlers.setUser(user);
            EntityHandlers.setUser(user);
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
                ViewHandlers.setUser(user);
                EntityHandlers.setUser(user);
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
        ViewHandlers.setUser(null);
        EntityHandlers.setUser(null);
    }

    private static void handleUnregister() {
        if (user == null || !user.isAuthenticated()) {
            println("User not logged in!");
            return;
        }
    
        try {
            user.delete();
            user = null;
            ViewHandlers.setUser(null);
            EntityHandlers.setUser(null);
        } catch (SQLException e) {
            println(e.getMessage());
        }
    }

    static void print(String str) {
        System.out.print(str);
    }

    static void println(String str) {
        System.out.println(str);
    }
}

class Arg {
    private String name;
    private String op;
    private String value;

    public Arg(String name, String op, String value) {
        this.name = name;
        this.op = op;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getOp() {
        return this.op;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.name + this.op + this.value;
    }
}