package entities;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

public class Work extends Entity {
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
     * @param user_id the user_id to set
     */
    public void setUserID(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the employer
     */
    public String getEmployer() {
        return employer;
    }

    /**
     * @param employer the employer to set
     */
    public void setEmployer(String employer) {
        this.employer = employer;
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
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
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
        return this.work_id.toString();
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