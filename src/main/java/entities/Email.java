package entities;

import java.sql.SQLException;
import java.util.Map;

public class Email extends Entity {
    private Integer user_id = null;
    private String address = null;

    public Email(Integer user_id, String address) {
        this.user_id = user_id;
        this.address = address;
    }

    /**
     * @return the user_id
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
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
        return this.address;
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