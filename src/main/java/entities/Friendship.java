package entities;

import java.sql.SQLException;
import java.util.Map;

public class Friendship extends Entity {
    private Integer user_id = null;
    private Integer friend_id = null;
    private String group_name = null;

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
    public String getGroupName() {
        return group_name;
    }

    @Override
    public void create() throws SQLException {

    }

    @Override
    public void delete() throws SQLException {

    }

    @Override
    public void load() throws SQLException {

    }

    @Override
    public String primaryKey() {
        return this.user_id + "-" + this.friend_id;
    }

    @Override
    public void store() throws SQLException {

    }

    @Override
    public void update(Map<String, String> properties) {

    }

    @Override
    public String toString() {
        return super.toString();
    }
}