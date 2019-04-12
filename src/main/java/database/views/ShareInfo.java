package database.views;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShareInfo extends View {
    private Integer share_id = null;
    private Integer post_id = null;
    private String  username = null;
    private Date share_time =null;

    /**
     * @return the share_id
     */
    public Integer getShareID() {
        return share_id;
    }

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
     * @return the update_time
     */
    public Date getShareTime() {
        return share_time;
    }

    public static ArrayList<ShareInfo> query(String queryString) throws SQLException {
        ShareInfo shareInfo;
        ResultSet result = naiveQuery(queryString);
        ArrayList<ShareInfo> shareInfoList = new ArrayList<>();
        while (result.next()) {
            shareInfo = new ShareInfo();
            shareInfo.share_id = result.getInt("share_id");
            shareInfo.post_id = result.getInt("post_id");
            shareInfo.username = result.getString("username");
            shareInfo.share_time = result.getDate("share_time");
            shareInfoList.add(shareInfo);
        }
        return shareInfoList;
    }

    @Override
    public String toString() {
        return "{" + 
            "share_id='" + share_id + "'" +
            ", post_id='" + post_id + "'" +
            ", username='" + username + "'" + 
            ", share_time='" + share_time + "'" + 
        "}";
    }
}