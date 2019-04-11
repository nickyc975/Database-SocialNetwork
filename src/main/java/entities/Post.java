package entities;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

public class Post extends Entity {
    private Integer post_id = null;
    private Integer user_id = null;
    private Boolean isPrivate = false;
    private String content = null;
    private Date update_time = null;

    public Post(Integer post_id) {
        this.post_id = post_id;
    }

    public Post(Integer user_id, Boolean isPrivate, String content) {
        this.user_id = user_id;
        this.isPrivate = isPrivate;
        this.content = content;
    }

    /**
     * @return the post_id
     */
    public Integer getPostID() {
        return post_id;
    }

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @return the isPrivate
     */
    public Boolean isPrivate() {
        return isPrivate;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the update_time
     */
    public Date getUpdateTime() {
        return new Date(update_time.getTime());
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
        return this.post_id.toString();
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