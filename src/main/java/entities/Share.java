package entities;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

public class Share extends Entity {
    private Integer share_id = null;
    private Integer user_id = null;
    private Integer post_id = null;
    private String content = null;
    private Date share_time = null;

    public Share(Integer share_id) {
        this.share_id = share_id;
    }

    public Share(Integer user_id, Integer post_id, String content, Date share_time) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.content = content;
        this.share_time = new Date(share_time.getTime());
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
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the share_time
     */
    public Date getShareTime() {
        return new Date(share_time.getTime());
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
        return this.share_id.toString();
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