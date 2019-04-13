package database.entities;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class Reply extends Entity {
    private static PreparedStatement createStatement;
    private static PreparedStatement loadStatement;
    private static PreparedStatement deleteStatement;

    private static String createString = "INSERT INTO `social_network`.`reply` " + 
                                         "(`user_id`, `post_id`, `content`) " + 
                                         "VALUES (?, ?, ?);";

    private static String loadString = "SELECT * FROM `social_network`.`reply` WHERE `reply_id` = ?;";

    private static String deleteString = "DELETE FROM `social_network`.`reply` WHERE `reply_id` = ?;";

    private Integer reply_id = null;
    private Integer user_id = null;
    private Integer post_id = null;
    private Integer replied_id = null;
    private String content = null;
    private Date reply_time = null;

    public Reply(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public Reply(Integer user_id, Integer post_id, String content) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.content = content;
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
    public String primaryKey() {
        return this.reply_id.toString();
    }

    @Override
    public void create() throws SQLException {
        if (createStatement == null) {
            createStatement = connection.prepareStatement(createString, Statement.RETURN_GENERATED_KEYS);
        }

        createStatement.setInt(1, this.user_id);
        createStatement.setInt(2, this.post_id);
        createStatement.setString(3, this.content);
        createStatement.executeUpdate();

        ResultSet keys = createStatement.getGeneratedKeys();
        if (keys.next()) {
            this.reply_id = keys.getInt(1);
        }
        keys.close();
        connection.commit();
        load();
    }

    @Override
    public void delete() throws SQLException {
        if (deleteStatement == null) {
            deleteStatement = connection.prepareStatement(deleteString);
        }

        deleteStatement.setInt(1, this.reply_id);
        deleteStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void load() throws SQLException {
        if (loadStatement == null) {
            loadStatement = connection.prepareStatement(loadString);
        }

        ResultSet result;
        loadStatement.setInt(1, this.reply_id);
        result = loadStatement.executeQuery();

        if (!result.next()) {
            throw new SQLException("User not exists!");
        }

        result.first();
        this.user_id = result.getInt("user_id");
        this.post_id = result.getInt("post_id");
        this.replied_id = result.getInt("replied_id");
        this.content = result.getString("content");
        this.reply_time = result.getDate("reply_time");
        result.close();
    }

    @Override
    public void store() throws SQLException {
        throw new RuntimeException("Operation not supported!");
    }

    @Override
    public void update(Map<String, String> properties) {
        String key, value;
        for (Entry<String, String> entry : properties.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            switch (key) {
                case "content":
                    this.content = value;
                default:
                    break;
                }
        }
    }

    public static ArrayList<Reply> query(String queryString) throws SQLException {
        Reply reply;
        ResultSet result = naiveQuery(queryString);
        ArrayList<Reply> replyList = new ArrayList<>();
        while (result.next()) {
            reply = new Reply(result.getInt("user_id"), result.getInt("post_id"), result.getString("content"));
            reply.reply_id = result.getInt("reply_id");
            replyList.add(reply);
        }
        return replyList;
    }

    @Override
    public String toString() {
        return "{" + 
            "reply_id=" + reply_id +
            ", post_id=" + post_id +
            ", replied_id=" + replied_id +
            ", content=" + content +
            ", reply_time=" + reply_time +
        "}";
    }
}