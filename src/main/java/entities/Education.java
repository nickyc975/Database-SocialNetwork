package entities;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

public class Education extends Entity {
    private Integer id = null;
    private Integer user_id = null;
    private String level = null;
    private Date start = null;
    private Date end = null;
    private String school = null;
    private String degree = null;

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
     * @param degree the degree to set
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUserID(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String primaryKey() {
        return this.id.toString();
    }

    @Override
    public void create() throws SQLException {

    }

    @Override
    public void load() throws SQLException {

    }

    @Override
    public void update(Map<String, String> args) {
        
    }

    @Override
    public void store() throws SQLException {

    }

    @Override
    public void delete() throws SQLException {

    }
}