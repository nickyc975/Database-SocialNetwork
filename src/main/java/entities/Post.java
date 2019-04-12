package entities;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class Post extends Entity {
    private static PreparedStatement createStatement;
    private static PreparedStatement loadStatement;
    private static PreparedStatement storeStatement;
    private static PreparedStatement deleteStatement;

    private static String createString = "INSERT INTO `social_network`.`post` " + 
                                         "(`user_id`, `private`, `content`) " + 
                                         "VALUES (?, ?, ?, ?);";

    private static String loadString = "SELECT * FROM `social_network`.`post` WHERE `post_id` = ?;";

    private static String storeString = "UPDATE `social_network`.`post` " + 
                                        "SET `private` = ?, `content` = ? " + 
                                        "WHERE `post_id` = ?;";

    private static String deleteString = "DELETE FROM `social_network`.`post` WHERE `post_id` = ?;";

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
        if (createStatement == null) {
            createStatement = connection.prepareStatement(createString, Statement.RETURN_GENERATED_KEYS);
        }

        createStatement.setInt(1, this.user_id);
        createStatement.setBoolean(2, this.isPrivate);
        createStatement.setString(3, this.content);
        createStatement.executeUpdate();

        ResultSet keys = createStatement.getGeneratedKeys();
        if (keys.next()) {
            this.post_id = keys.getInt(1);
        }
        keys.close();
        connection.commit();
    }

    @Override
    public void delete() throws SQLException {
        if (deleteStatement == null) {
            deleteStatement = connection.prepareStatement(deleteString);
        }

        deleteStatement.setInt(1, this.post_id);
        deleteStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void load() throws SQLException {
        if (loadStatement == null) {
            loadStatement = connection.prepareStatement(loadString);
        }

        ResultSet result;
        loadStatement.setInt(1, this.post_id);
        result = loadStatement.executeQuery();

        if (!result.next()) {
            throw new SQLException("User not exists!");
        }

        result.first();
        this.user_id = result.getInt("user_id");
        this.isPrivate= result.getBoolean("private");
        this.content = result.getString("content");
        this.update_time = result.getDate("update_time");
        result.close();
    }

    @Override
    public String primaryKey() {
        return this.post_id.toString();
    }

    @Override
    public void store() throws SQLException {
        if (storeStatement == null) {
            storeStatement = connection.prepareStatement(storeString);
        }

        storeStatement.setBoolean(1, this.isPrivate);
        storeStatement.setString(2, this.content);

        storeStatement.executeUpdate();
        connection.commit();
    }

    @Override
    public void update(Map<String, String> properties) {
        String key, value;
        for (Entry<String, String> entry : properties.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            switch (key) {
                case "private":
                    this.isPrivate = Boolean.parseBoolean(value);
                    break;
                case "content":
                    this.content = value;
                default:
                    break;
                }
        }
    }

    public static ArrayList<Post> query(String queryString) throws SQLException {
        Post post;
        ResultSet result = naiveQuery(queryString);
        ArrayList<Post> postList = new ArrayList<>();
        while (result.next()) {
            post = new Post(result.getInt("user_id"), result.getBoolean("private"), result.getString("content"));
            post.post_id = result.getInt("post_id");
            postList.add(post);
        }
        return postList;
    }

    @Override
    public String toString() {
        return "{" + 
            "post_id=" + post_id + 
            "private=" + isPrivate +
            "content=" + content +
            "update_time=" + update_time +
        "}";
    }
}