package entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.InvalidDataException;

public class Friendship extends Entity {
    private static PreparedStatement createStatement;
    private static PreparedStatement loadStatement;
    private static PreparedStatement storeStatement;
    private static PreparedStatement deleteStatement;
    
    private static String createString = "INSERT INTO `social_network`.`friendship` " + 
                                         "(`user_id`, `friend_id`, `group_id`) " + 
                                         "VALUES (?, ?, ?);";

    private static String loadString = "SELECT * FROM `social_network`.`friendship` " + 
                                       "WHERE `user_id` = ? AND `friend_id` = ?;";

    private static String storeString = "UPDATE `social_network`.`friendship` " + 
                                        "SET `group_id` = ? " + 
                                        "WHERE `user_id` = ? AND `friend_id` = ?;";

    private static String deleteString = "DELETE FROM `social_network`.`friendship` " + 
                                         "WHERE `user_id` = ? AND `friend_id` = ?;";

    private Integer user_id = null;
    private Integer friend_id = null;
    private Integer group_id = null;

    public Friendship(Integer user_id, Integer friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @return the friend_id
     */
    public Integer getFriendID() {
        return friend_id;
    }

    /**
     * @return the group_name
     */
    public Integer getGroupID() {
        return group_id;
    }

    @Override
    public void create() throws SQLException {
        if (createStatement == null) {
            createStatement = connection.prepareStatement(createString, Statement.RETURN_GENERATED_KEYS);
        }

        createStatement.setInt(1, this.user_id);
        createStatement.setInt(2, this.friend_id);
        createStatement.setInt(3, this.group_id);
        createStatement.executeUpdate();

        connection.commit();
    }

    @Override
    public void delete() throws SQLException {
        if (deleteStatement == null) {
            deleteStatement = connection.prepareStatement(deleteString);
        }

        deleteStatement.setInt(1, this.user_id);
        deleteStatement.setInt(2, this.friend_id);
        deleteStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void load() throws SQLException {
        if (loadStatement == null) {
            loadStatement = connection.prepareStatement(loadString);
        }

        ResultSet result;
        loadStatement.setInt(1, this.user_id);
        loadStatement.setInt(2, this.friend_id);
        result = loadStatement.executeQuery();

        if (!result.next()) {
            throw new SQLException("User not exists!");
        }

        result.first();
        this.group_id = result.getInt("group_id");
        result.close();
    }

    @Override
    public String primaryKey() {
        return this.user_id + "-" + this.friend_id;
    }

    @Override
    public void store() throws SQLException {
        if (storeStatement == null) {
            storeStatement = connection.prepareStatement(storeString);
        }

        storeStatement.setInt(1, this.group_id);
        storeStatement.setInt(2, this.user_id);
        storeStatement.setInt(3, this.friend_id);

        storeStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void update(Map<String, String> properties) throws InvalidDataException {
        String key, value;
        for (Entry<String, String> entry : properties.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            switch (key) {
                case "group_id":
                    this.group_id = Integer.parseInt(value);
                    break;
                default:
                    break;
                }
        }

    }

    public static ArrayList<Friendship> query(String queryString) throws SQLException {
        Friendship ship;
        ResultSet result = naiveQuery(queryString);
        ArrayList<Friendship> shipList = new ArrayList<>();
        while (result.next()) {
            ship = new Friendship(result.getInt("user_id"), result.getInt("friend_id"));
            ship.group_id = result.getInt("group_id");
            shipList.add(ship);
        }
        return shipList;
    }

    @Override
    public String toString() {
        return "{"+ 
                "user_id=" + user_id +
                "friend_id=" + friend_id +
                "group_id=" + group_id +
            "}";
    }
}