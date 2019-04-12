package database.views;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostInfo extends View {
    private Integer post_id = null;
    private String  username = null;
    private String content = null;
    private Date update_time =null;

    /**
     * @return the post_id
     */
    public Integer getPostID() {
        return post_id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
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
        return update_time;
    }

    public static ArrayList<PostInfo> query(String queryString) throws SQLException {
        PostInfo postInfo;
        ResultSet result = naiveQuery(queryString);
        ArrayList<PostInfo> postInfoList = new ArrayList<>();
        while (result.next()) {
            postInfo = new PostInfo();
            postInfo.post_id = result.getInt("post_id");
            postInfo.username = result.getString("username");
            postInfo.content = result.getString("content");
            postInfo.update_time = result.getDate("update_time");
            postInfoList.add(postInfo);
        }
        return postInfoList;
    }

    @Override
    public String toString() {
        return "{" + 
            "post_id='" + post_id + "'" +
            ", username='" + username + "'" + 
            ", content='" + content + "'" +
            ", update_time='" + update_time + "'" + 
        "}";
    }
}