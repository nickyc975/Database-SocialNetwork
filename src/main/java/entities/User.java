package entities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class User extends Entity {
    public static final String MALE = "MALE";
    public static final String FEMALE = "FEMALE";

    private static PreparedStatement createStatement;
    private static PreparedStatement loadStatement;
    private static PreparedStatement storeStatement;
    private static PreparedStatement deleteStatement;

    private static String createString = "INSERT INTO `social_network`.`user` (`username`, `password`) VALUES (?, ?);";
    private static String loadString = "SELECT * FROM `social_network`.`user` WHERE `username` = ?;";
    private static String storeString = "UPDATE `social_network`.`user` SET `name` = ?, `gender` = ?, `birthday` = ?, `address` = ? WHERE `username` = ?;";
    private static String deleteString = "DELETE FROM `social_network`.`user` WHERE `username` = ?;";

    private boolean authenticated = false;
    private Integer user_id = null;
    private String username = null;
    private String password = null;
    private String name = null;
    private String gender = null;
    private Date birthday = null;
    private String address = null;

    public User(String username, String password) {
        this.username = username;
        this.password = hash(password);
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Login user.
     */
    public boolean login() throws SQLException {
        if (authenticated) {
            return authenticated;
        }

        ResultSet result;
        String passwd;
        if (loadStatement == null) {
            loadStatement = connection.prepareStatement(loadString);
        }

        loadStatement.setString(1, this.username);
        result = loadStatement.executeQuery();
        if (result.next()) {
            result.first();
            passwd = result.getString("password");
            result.close();
            authenticated = passwd.equals(this.password);
            return authenticated;
        }

        throw new SQLException("User not exists!");
    }

    /**
     * Hash password.
     * 
     * @param str original password text.
     * @return md5 hash of the original text.
     */
    private String hash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException ignored) {

        }
        return str;
    }

    @Override
    public String primaryKey() {
        return this.user_id.toString();
    }

    @Override
    public void create() throws SQLException {
        if (createStatement == null) {
            createStatement = connection.prepareStatement(createString);
        }

        createStatement.setString(1, this.username);
        createStatement.setString(2, this.password);
        createStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void load() throws SQLException {
        if (!authenticated) {
            throw new SQLException("User not authenticated!");
        }

        ResultSet result;
        if (loadStatement == null) {
            loadStatement = connection.prepareStatement(loadString);
        }

        loadStatement.setString(1, this.username);
        result = loadStatement.executeQuery();
        if (result.next()) {
            result.first();
            this.user_id = result.getInt("user_id");
            this.password = result.getString("password");
            this.name = result.getString("name");
            this.gender = result.getString("gender");
            this.birthday = result.getDate("birthday");
            this.address = result.getString("address");
            result.close();
            return;
        }

        throw new SQLException("User not exists!");
    }

    @Override
    public void update(Map<String, String> properties) {
        String key, value;
        for (Entry<String, String> entry: properties.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            switch(key) {
                case "name":
                    this.name = value;
                    break;
                case "password":
                    this.password = value;
                    break;
                case "gender":
                    this.gender = value;
                    break;
                case "birthday":
                    this.birthday = parseDate(value);
                    break;
                case "address":
                    this.address = value;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void store() throws SQLException {
        if (!authenticated) {
            throw new SQLException("User not authenticated!");
        }

        if (storeStatement == null) {
            storeStatement = connection.prepareStatement(storeString);
        }

        storeStatement.setString(1, this.name);
        storeStatement.setString(2, this.gender);
        storeStatement.setDate(3, this.birthday);
        storeStatement.setString(4, this.address);
        storeStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void delete() throws SQLException {
        if (!authenticated) {
            throw new SQLException("User not authenticated!");
        }

        if (deleteStatement == null) {
            deleteStatement = connection.prepareStatement(deleteString);
        }

        deleteStatement.setString(1, this.username);
        deleteStatement.executeUpdate();
        connection.commit();
    }

    public static ArrayList<User> query(String queryString) throws SQLException {
        User user;
        ResultSet result = naiveQuery(queryString);
        ArrayList<User> userList = new ArrayList<>();
        while (result.next()) {
            user = new User(result.getString("username"), result.getString("password"));
            user.user_id = result.getInt("user_id");
            user.name = result.getString("name");
            user.gender = result.getString("gender");
            user.birthday = result.getDate("birthday");
            user.address = result.getString("address");
            user.authenticated = true;
            userList.add(user);
        }
        return userList;
    }

    @Override
    public String toString() {
        return "{" +
            ", username='" + username + "'" +
            ", name='" + name + "'" +
            ", gender='" + gender + "'" +
            ", birthday='" + birthday + "'" +
            ", address='" + address + "'" +
            "}";
    }
}