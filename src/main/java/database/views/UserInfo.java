package database.views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserInfo extends View {
    private Integer user_id = null;
    private String username = null;
    private String name = null;

    private UserInfo() {}

    /**
     * @return the user_id
     */
    public Integer getUserID() {
        return user_id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    public static ArrayList<UserInfo> query(String queryString) throws SQLException {
        UserInfo userInfo;
        ResultSet result = naiveQuery(queryString);
        ArrayList<UserInfo> userInfoList = new ArrayList<>();
        while (result.next()) {
            userInfo = new UserInfo();
            userInfo.user_id = result.getInt("user_id");
            userInfo.username = result.getString("username");
            userInfo.name = result.getString("name");
            userInfoList.add(userInfo);
        }
        return userInfoList;
    }

    @Override
    public String toString() {
        return "{" + 
               "username='" + username + "'" + 
               ", name='" + name + "'" + 
            "}";
    }
}