package database.views;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReplyInfo extends View {
    private Integer reply_id = null;
    private Integer post_id = null;
    private Integer replied_id = null;
    private String  username = null;
    private String content = null;
    private Date reply_time =null;

    /**
     * @return the reply_id
     */
    public Integer getReplyID() {
        return reply_id;
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
    public Date getReplyTime() {
        return reply_time;
    }

    public static ArrayList<ReplyInfo> query(String queryString) throws SQLException {
        ReplyInfo replyInfo;
        ResultSet result = naiveQuery(queryString);
        ArrayList<ReplyInfo> replyInfoList = new ArrayList<>();
        while (result.next()) {
            replyInfo = new ReplyInfo();
            replyInfo.reply_id = result.getInt("reply_id");
            replyInfo.post_id = result.getInt("post_id");
            replyInfo.replied_id = result.getInt("replied_id");
            replyInfo.username = result.getString("username");
            replyInfo.content = result.getString("content");
            replyInfo.reply_time = result.getDate("reply_time");
            replyInfoList.add(replyInfo);
        }
        return replyInfoList;
    }

    @Override
    public String toString() {
        return "{" + 
            "reply_id='" + reply_id + "'" +
            ", post_id='" + post_id + "'" +
            ", replied_id='" + replied_id + "'" +
            ", username='" + username + "'" + 
            ", content='" + content + "'" +
            ", reply_time='" + reply_time + "'" + 
        "}";
    }
}