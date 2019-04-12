package database.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Friend extends View {
    private Integer user_id = null;
    private Integer friend_id = null;
    private Integer group_id = null;
    private String username = null;

    private Friend() {}

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @return the friend_id
     */
    public Integer getFriendID() {
        return friend_id;
    }

    /**
     * @return the group_id
     */
    public Integer getGroupID() {
        return group_id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    public static ArrayList<Friend> query(String queryString) throws SQLException {
        Friend friend;
        ResultSet result = naiveQuery(queryString);
        ArrayList<Friend> friendList = new ArrayList<>();
        while (result.next()) {
            friend = new Friend();
            friend.user_id = result.getInt("user_id");
            friend.friend_id = result.getInt("friend_id");
            friend.group_id = result.getInt("group_id");
            friend.username = result.getString("username");
            friendList.add(friend);
        }
        return friendList;
    }
}