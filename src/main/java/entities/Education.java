package entities;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

public class Education extends Entity {
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

    }

    @Override
    public void load() throws SQLException {

    }

    @Override
    public void update(Map<String, String> properties) {
        
    }

    @Override
    public void store() throws SQLException {

    }

    @Override
    public void delete() throws SQLException {

    }

    @Override
    public String toString() {
        return super.toString();
    }
}