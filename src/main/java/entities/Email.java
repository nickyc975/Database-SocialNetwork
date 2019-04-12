package entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import exceptions.InvalidDataException;

public class Email extends Entity {
    private static PreparedStatement createStatement;
    private static PreparedStatement deleteStatement;

    private static String createString = "INSERT INTO `social_network`.`email` " + 
                                         "(`user_id`, `address`) " + 
                                         "VALUES " + 
                                         "(?, ?);";

    private static String deleteString = "DELETE FROM `social_network`.`email` WHERE `address` = ?;";

    private Integer user_id = null;
    private String address = null;

    public Email(Integer user_id, String address) {
        this.user_id = user_id;
        this.address = address;
    }

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    @Override
    public String primaryKey() {
        return this.address;
    }

    @Override
    public void create() throws SQLException {
        if (createStatement == null) {
            createStatement = connection.prepareStatement(createString);
        }

        createStatement.setInt(1, this.user_id);
        createStatement.setString(2, this.address);
        createStatement.executeUpdate();

        connection.commit();
    }

    @Override
    public void delete() throws SQLException {
        if (deleteStatement == null) {
            deleteStatement = connection.prepareStatement(deleteString);
        }

        deleteStatement.setString(1, this.address);
        deleteStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void load() throws SQLException {
        throw new RuntimeException("Operation not allowed!");
    }

    @Override
    public void store() throws SQLException {
        throw new RuntimeException("Operation not allowed!");
    }

    @Override
    public void update(Map<String, String> properties) throws InvalidDataException {
        throw new RuntimeException("Operation not allowed!");
    }

    public static ArrayList<Email> query(String queryString) throws SQLException {
        Email email;
        ResultSet result = naiveQuery(queryString);
        ArrayList<Email> emailList = new ArrayList<>();
        while (result.next()) {
            email = new Email(result.getInt("user_id"), result.getString("address"));
            emailList.add(email);
        }
        return emailList;
    }

    @Override
    public String toString() {
        return "{address='" + this.address+ "'}";
    }
}