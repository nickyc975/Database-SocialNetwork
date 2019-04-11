package entities;

import java.sql.SQLException;
import java.util.Map;

public class FriendGroup extends Entity {
    private Integer user_id = null;
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

    }

    @Override
    public void delete() throws SQLException {

    }

    @Override
    public void load() throws SQLException {

    }

    @Override
    public String primaryKey() {
        return this.user_id + "-" + this.group_name;
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