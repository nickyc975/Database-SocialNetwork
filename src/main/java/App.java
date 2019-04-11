import java.sql.*;
import java.util.ArrayList;

import entities.*;

public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/social_network?user=root&password=123456");
            Entity.setConnection(connection);

            ArrayList<User> list = User.query("SELECT * FROM `user`;");
            for (User u : list) {
                System.out.println(u.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
