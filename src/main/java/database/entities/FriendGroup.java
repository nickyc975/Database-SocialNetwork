package database.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import database.exceptions.InvalidDataException;

public class FriendGroup extends Entity {
    private static PreparedStatement createStatement;
    private static PreparedStatement loadStatement;
    private static PreparedStatement storeStatement;
    private static PreparedStatement deleteStatement;

    private static String createString = "INSERT INTO `social_network`.`friend_group` " + 
                                         "(`user_id`, `group_name`) " + 
                                         "VALUES (?, ?);";

    private static String loadString = "SELECT * FROM `social_network`.`friend_group` WHERE `group_id` = ?;";

    private static String storeString = "UPDATE `social_network`.`friend_group` " + 
                                        "SET `group_name` = ? " + 
                                        "WHERE `group_id` = ?;";

    private static String deleteString = "DELETE FROM `social_network`.`friend_group` WHERE `group_id` = ?;";

    private Integer user_id = null;
    private Integer group_id = null;
    private String group_name = null;

    public FriendGroup (Integer user_id, String group_name) {
        this.user_id = user_id;
        this.group_name = group_name;
    }

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @return the group_name
     */
    public String getGroupName() {
        return group_name;
    }

    @Override
    public void create() throws SQLException {
        if (createStatement == null) {
            createStatement = connection.prepareStatement(createString, Statement.RETURN_GENERATED_KEYS);
        }

        createStatement.setInt(1, this.user_id);
        createStatement.setString(2, this.group_name);
        createStatement.executeUpdate();

        ResultSet keys = createStatement.getGeneratedKeys();
        if (keys.next()) {
            this.group_id = keys.getInt(1);
        }
        keys.close();
        connection.commit();
    }

    @Override
    public void delete() throws SQLException {
        if (deleteStatement == null) {
            deleteStatement = connection.prepareStatement(deleteString);
        }

        deleteStatement.setInt(1, this.group_id);
        deleteStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void load() throws SQLException {
        if (loadStatement == null) {
            loadStatement = connection.prepareStatement(loadString);
        }

        ResultSet result;
        loadStatement.setInt(1, this.group_id);
        result = loadStatement.executeQuery();

        if (!result.next()) {
            throw new SQLException("User not exists!");
        }

        result.first();
        this.user_id = result.getInt("user_id");
        this.group_name = result.getString("group_name");
        result.close();
    }

    @Override
    public String primaryKey() {
        return this.group_id.toString();
    }

    @Override
    public void store() throws SQLException {
        if (storeStatement == null) {
            storeStatement = connection.prepareStatement(storeString);
        }

        storeStatement.setInt(1, this.user_id);
        storeStatement.setString(2, this.group_name);

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
                case "level":
                    this.group_name = value;
                    break;
                default:
                    break;
                }
        }
    }

    public static ArrayList<FriendGroup> query(String queryString) throws SQLException {
        FriendGroup group;
        ResultSet result = naiveQuery(queryString);
        ArrayList<FriendGroup> groupList = new ArrayList<>();
        while (result.next()) {
            group = new FriendGroup(result.getInt("user_id"), result.getString("group_name"));
            group.group_id = result.getInt("group_id");
            groupList.add(group);
        }
        return groupList;
    }

    @Override
    public String toString() {
        return "{group_name='" + this.group_name + "'}";
    }
}