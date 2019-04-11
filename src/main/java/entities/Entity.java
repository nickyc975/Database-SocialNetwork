package entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class Entity {
    private static Statement statement;
    protected static Connection connection;

    public static void setConnection(Connection connection) throws SQLException {
        Entity.connection = connection;
        Entity.connection.setAutoCommit(false);
    }

    public abstract String primaryKey();

    public abstract void create() throws SQLException;

    public abstract void load() throws SQLException;
    
    public abstract void store() throws SQLException;

    public abstract void delete() throws SQLException;

    public static ArrayList<? extends Entity> query(String queryString) throws SQLException {
        throw new RuntimeException("This method is not implemented in this class!");
    }

    public static ResultSet naiveQuery(String queryString) throws SQLException {
        if (statement == null) {
            statement = connection.createStatement();
        }

        return statement.executeQuery(queryString);
    }
}