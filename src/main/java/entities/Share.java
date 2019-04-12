package entities;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class Share extends Entity {
    private static PreparedStatement createStatement;
    private static PreparedStatement loadStatement;
    private static PreparedStatement deleteStatement;

    private static String createString = "INSERT INTO `social_network`.`share` " + 
                                         "(`user_id`, `post_id`, `content`) " + 
                                         "VALUES (?, ?, ?);";

    private static String loadString = "SELECT * FROM `social_network`.`share` WHERE `share_id` = ?;";

    private static String deleteString = "DELETE FROM `social_network`.`share` WHERE `share_id` = ?;";

    private Integer share_id = null;
    private Integer user_id = null;
    private Integer post_id = null;
    private String content = null;
    private Date share_time = null;

    public Share(Integer share_id) {
        this.share_id = share_id;
    }

    public Share(Integer user_id, Integer post_id, String content) {
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
    public String primaryKey() {
        return this.share_id.toString();
    }

    @Override
    public void create() throws SQLException {
        if (createStatement == null) {
            createStatement = connection.prepareStatement(createString, Statement.RETURN_GENERATED_KEYS);
        }

        createStatement.setInt(1, this.user_id);
        createStatement.setInt(2, this.post_id);
        createStatement.setString(4, this.content);
        createStatement.executeUpdate();

        ResultSet keys = createStatement.getGeneratedKeys();
        if (keys.next()) {
            this.share_id = keys.getInt(1);
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

        deleteStatement.setInt(1, this.share_id);
        deleteStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void load() throws SQLException {
        if (loadStatement == null) {
            loadStatement = connection.prepareStatement(loadString);
        }

        ResultSet result;
        loadStatement.setInt(1, this.share_id);
        result = loadStatement.executeQuery();

        if (!result.next()) {
            throw new SQLException("User not exists!");
        }

        result.first();
        this.user_id = result.getInt("user_id");
        this.post_id = result.getInt("post_id");
        this.content = result.getString("content");
        this.share_time = result.getDate("share_time");
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

    public static ArrayList<Share> query(String queryString) throws SQLException {
        Share share;
        ResultSet result = naiveQuery(queryString);
        ArrayList<Share> shareList = new ArrayList<>();
        while (result.next()) {
            share = new Share(result.getInt("user_id"), result.getInt("post_id"), result.getString("content"));
            share.share_id = result.getInt("share_id");
            shareList.add(share);
        }
        return shareList;
    }

    @Override
    public String toString() {
        return "{" + 
            "share_id=" + share_id +
            ", post_id=" + post_id +
            ", content=" + content +
            ", share_time=" + share_time +
        "}";
    }
}