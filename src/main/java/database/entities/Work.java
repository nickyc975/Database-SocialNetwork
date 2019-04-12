package database.entities;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import database.exceptions.InvalidDataException;

public class Work extends Entity {
    private static PreparedStatement createStatement;
    private static PreparedStatement loadStatement;
    private static PreparedStatement storeStatement;
    private static PreparedStatement deleteStatement;

    private static String createString = "INSERT INTO `social_network`.`work` " + 
                                         "(`user_id`, `employer`, `start`, `end`, `position`) " + 
                                         "VALUES " + 
                                         "(?, ?, ?, ?, ?);";

    private static String loadString = "SELECT * FROM `social_network`.`work` WHERE `work_id` = ?;";

    private static String storeString = "UPDATE `social_network`.`work` " + 
                                        "SET " + 
                                        "`employer` = ?, `start` = ?, `end` = ?, `position` = ? " + 
                                        "WHERE `work_id` = ?;";

    private static String deleteString = "DELETE FROM `social_network`.`work` WHERE `work_id` = ?;";

    private Integer work_id = null;
    private Integer user_id = null;
    private String employer = null;
    private Date start = null;
    private Date end = null;
    private String position = null;

    public Work(Integer work_id) {
        this.work_id = work_id;
    }

    public Work(Integer user_id, String employer, Date start, Date end, String position) {
        this.user_id = user_id;
        this.employer = employer;
        this.start = start;
        this.end = end;
        this.position = position;
    }

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @return the employer
     */
    public String getEmployer() {
        return employer;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return new Date(start.getTime());
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return new Date(end.getTime());
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    @Override
    public String primaryKey() {
        return this.work_id.toString();
    }

    @Override
    public void create() throws SQLException {
        if (createStatement == null) {
            createStatement = connection.prepareStatement(createString, Statement.RETURN_GENERATED_KEYS);
        }

        createStatement.setInt(1, this.user_id);
        createStatement.setString(2, this.employer);
        createStatement.setDate(3, this.start);
        createStatement.setDate(4, this.end);
        createStatement.setString(5, this.position);
        createStatement.executeUpdate();

        ResultSet keys = createStatement.getGeneratedKeys();
        if (keys.next()) {
            this.user_id = keys.getInt(1);
        }
        keys.close();
        connection.commit();
    }

    @Override
    public void load() throws SQLException {
        if (loadStatement == null) {
            loadStatement = connection.prepareStatement(loadString);
        }

        ResultSet result;
        loadStatement.setInt(1, this.work_id);
        result = loadStatement.executeQuery();

        if (!result.next()) {
            throw new SQLException("User not exists!");
        }

        result.first();
        this.user_id = result.getInt("user_id");
        this.employer = result.getString("employer");
        this.start = result.getDate("start");
        this.end = result.getDate("end");
        this.position = result.getString("position");
        result.close();
    }

    @Override
    public void update(Map<String, String> properties) throws InvalidDataException {
        String key, value;
        for (Entry<String, String> entry : properties.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            switch (key) {
                case "employer":
                    this.employer = value;
                    break;
                case "start":
                    this.start = parseDate(value);
                    break;
                case "end":
                    this.end = parseDate(value);
                    break;
                case "position":
                    this.position = value;
                    break;
                default:
                    break;
                }
        }
    }

    @Override
    public void store() throws SQLException {
        if (storeStatement == null) {
            storeStatement = connection.prepareStatement(storeString);
        }

        storeStatement.setString(1, this.employer);
        storeStatement.setDate(2, this.start);
        storeStatement.setDate(3, this.end);
        storeStatement.setString(4, this.position);
        storeStatement.setInt(6, this.work_id);

        storeStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void delete() throws SQLException {
        if (deleteStatement == null) {
            deleteStatement = connection.prepareStatement(deleteString);
        }

        deleteStatement.setInt(1, this.work_id);
        deleteStatement.executeUpdate();
        connection.commit();
    }

    public static ArrayList<Work> query(String queryString) throws SQLException {
        Work work;
        ResultSet result = naiveQuery(queryString);
        ArrayList<Work> workList = new ArrayList<>();
        while (result.next()) {
            work = new Work(result.getInt("Work_id"));
            work.user_id = result.getInt("user_id");
            work.employer = result.getString("employer");
            work.start = result.getDate("start");
            work.end = result.getDate("end");
            work.position = result.getString("position");
            workList.add(work);
        }
        return workList;
    }

    @Override
    public String toString() {
        return "{" + 
               ", eduaction_id='" + this.work_id + "'" + 
               ", employer='" + this.employer + "'" + 
               ", start='" + this.start + "'" + 
               ", end='" + this.end + "'" + 
               ", position='" + this.position + "'" + 
            "}";
    }
}