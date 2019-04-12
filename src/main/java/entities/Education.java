package entities;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.InvalidDataException;

public class Education extends Entity {
    private static PreparedStatement createStatement;
    private static PreparedStatement loadStatement;
    private static PreparedStatement storeStatement;
    private static PreparedStatement deleteStatement;

    private static String createString = "INSERT INTO `social_network`.`education` " + 
                                         "(`user_id`, `level`, `start`, `end`, `school`, `degree`) " + 
                                         "VALUES " + 
                                         "(?, ?, ?, ?, ?, ?);";

    private static String loadString = "SELECT * FROM `social_network`.`education` WHERE `education_id` = ?;";

    private static String storeString = "UPDATE `social_network`.`education` " + 
                                        "SET " + 
                                        "`level` = ?, `start` = ?, `end` = ?, `school` = ?, `degree` = ? " + 
                                        "WHERE `education_id` = ?;";

    private static String deleteString = "DELETE FROM `social_network`.`education` WHERE `education_id` = ?;";

    private Integer education_id = null;
    private Integer user_id = null;
    private String level = null;
    private Date start = null;
    private Date end = null;
    private String school = null;
    private String degree = null;

    public Education(Integer education_id) {
        this.education_id = education_id;
    }

    public Education(Integer user_id, String level, Date start, Date end, String school, String degree) {
        this.user_id = user_id;
        this.level = level;
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        this.school = school;
        this.degree = degree;
    }

    /**
     * @return the degree
     */
    public String getDegree() {
        return degree;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return new Date(end.getTime());
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return new Date(start.getTime());
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    @Override
    public String primaryKey() {
        return this.education_id.toString();
    }

    @Override
    public void create() throws SQLException {
        if (createStatement == null) {
            createStatement = connection.prepareStatement(createString, Statement.RETURN_GENERATED_KEYS);
        }

        createStatement.setInt(1, this.user_id);
        createStatement.setString(2, this.level);
        createStatement.setDate(3, this.start);
        createStatement.setDate(4, this.end);
        createStatement.setString(5, this.school);
        createStatement.setString(6, this.degree);
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
        loadStatement.setInt(1, this.education_id);
        result = loadStatement.executeQuery();

        if (!result.next()) {
            throw new SQLException("User not exists!");
        }

        result.first();
        this.user_id = result.getInt("user_id");
        this.level = result.getString("level");
        this.start = result.getDate("start");
        this.end = result.getDate("end");
        this.school = result.getString("school");
        this.degree = result.getString("degree");
        result.close();
    }

    @Override
    public void update(Map<String, String> properties) throws InvalidDataException {
        String key, value;
        for (Entry<String, String> entry : properties.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            switch (key) {
                case "level":
                    this.level = value;
                    break;
                case "start":
                    this.start = parseDate(value);
                    break;
                case "end":
                    this.end = parseDate(value);
                    break;
                case "school":
                    this.school = value;
                    break;
                case "degree":
                    this.degree = value;
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

        storeStatement.setString(1, this.level);
        storeStatement.setDate(2, this.start);
        storeStatement.setDate(3, this.end);
        storeStatement.setString(4, this.school);
        storeStatement.setString(5, this.degree);
        storeStatement.setInt(6, this.education_id);

        storeStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void delete() throws SQLException {
        if (deleteStatement == null) {
            deleteStatement = connection.prepareStatement(deleteString);
        }

        deleteStatement.setInt(1, this.education_id);
        deleteStatement.executeUpdate();
        connection.commit();
    }

    public static ArrayList<Education> query(String queryString) throws SQLException {
        Education education;
        ResultSet result = naiveQuery(queryString);
        ArrayList<Education> educationList = new ArrayList<>();
        while (result.next()) {
            education = new Education(result.getInt("education_id"));
            education.user_id = result.getInt("user_id");
            education.level = result.getString("level");
            education.start = result.getDate("start");
            education.end = result.getDate("end");
            education.school = result.getString("school");
            education.degree = result.getString("degree");
            educationList.add(education);
        }
        return educationList;
    }

    @Override
    public String toString() {
        return "{" + 
               ", eduaction_id='" + this.education_id + "'" + 
               ", level='" + this.level + "'" + 
               ", start='" + this.start + "'" + 
               ", end='" + this.end + "'" + 
               ", school='" + this.school + "'" + 
               ", degree='" + this.degree + "'" + 
            "}";
    }
}