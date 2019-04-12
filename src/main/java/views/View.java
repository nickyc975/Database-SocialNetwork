package views;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import exceptions.InvalidDataException;

public class View {
    /**
     * Global DateFormat instance.
     */
    private static DateFormat format;

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
        View.connection = connection;
        View.connection.setAutoCommit(false);
    }

    /**
     * Parse date object from string.
     * 
     * @param date date string with format yyyy-MM-dd.
     * @return Date object.
     * @throws InvalidDataException date format error.
     */
    public static Date parseDate(String date) throws InvalidDataException {
        if (format == null) {
            format = new SimpleDateFormat("yyyy-MM-dd");
        }

        try {
            return new Date(format.parse(date).getTime());
        } catch (ParseException ignored) {
            throw new InvalidDataException(date, "Valid date format: yyyy-MM-dd, eg. 2019-04-12");
        }
    };

    /**
     * Execute query and return objects directly.
     * 
     * @param queryString sql query string.
     * @return object list.
     * @throws SQLException query error.
     */
    public static ArrayList<? extends View> query(String queryString) throws SQLException {
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