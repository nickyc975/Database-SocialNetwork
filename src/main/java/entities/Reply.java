package entities;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

public class Reply extends Entity {
    private Integer reply_id = null;
    private Integer user_id = null;
    private Integer post_id = null;
    private Integer replied_id = null;
    private String content = null;
    private Date reply_time = null;

    public Reply(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public Reply(Integer user_id, Integer post_id, Integer replied_id, String content, Date reply_time) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.replied_id = replied_id;
        this.content = content;
        this.reply_time = new Date(reply_time.getTime());
    }

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @return the post_id
     */
    public Integer getPostID() {
        return post_id;
    }

    /**
     * @return the replied_id
     */
    public Integer getRepliedID() {
        return replied_id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the reply_time
     */
    public Date getReplyTime() {
        return new Date(reply_time.getTime());
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
        return this.reply_id.toString();
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