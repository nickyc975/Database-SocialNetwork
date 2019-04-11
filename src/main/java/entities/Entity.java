package entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

/**
 * Abstract entity class.
 */
public abstract class Entity {
    /**
     * Global query statement.
     */
    private static Statement statement;

    /**
     * Database connection.
     */
    protected static Connection connection;

    /**
     * Set connection object.
     * 
     * @param connection Database connection object.
     */
    public static void setConnection(Connection connection) throws SQLException {
        Entity.connection = connection;
        Entity.connection.setAutoCommit(false);
    }

    /**
     * Get primary key of the entity object.
     * 
     * @return primary key.
     */
    public abstract String primaryKey();

    /**
     * Insert the newly created object into database.
     * 
     * @throws SQLException insert error.
     */
    public abstract void create() throws SQLException;

    /**
     * Load infomation of the object from database.
     * 
     * @throws SQLException load error.
     */
    public abstract void load() throws SQLException;

    /**
     * Update properties of the object with given args.
     * 
     * @param properties porpertoies.
     */
    public abstract void update(Map<String, String> properties);

    /**
     * Store properties of the object into database.
     * 
     * @throws SQLException store error.
     */
    public abstract void store() throws SQLException;

    /**
     * Delete the object from database.
     * 
     * @throws SQLException delete error.
     */
    public abstract void delete() throws SQLException;

    /**
     * Parse date object from string.
     * 
     * @param date date string with format yyyy-MM-dd.
     * @return Date object.
     */
    public static Date parseDate(String date) {
        String[] parts = date.split("-");
        return new Date(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    };

    /**
     * Execute query and return objects directly.
     * 
     * @param queryString sql query string.
     * @return object list.
     * @throws SQLException query error.
     */
    public static ArrayList<? extends Entity> query(String queryString) throws SQLException {
        throw new RuntimeException("This method is not implemented in this class!");
    }

    /**
     * Execute query.
     * 
     * @param queryString sql query string.
     * @return ResultSet object.
     * @throws SQLException query error.
     */
    public static ResultSet naiveQuery(String queryString) throws SQLException {
        if (statement == null) {
            statement = connection.createStatement();
        }

        return statement.executeQuery(queryString);
    }
}