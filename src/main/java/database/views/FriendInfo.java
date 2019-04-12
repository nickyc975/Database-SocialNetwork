package database.views;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendInfo extends View {
    private Integer user_id = null;
    private String username = null;
    private String name = null;
    private String gender = null;
    private Date birthday = null;
    private String address = null;

    private FriendInfo() {}

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

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    public static ArrayList<FriendInfo> query(String queryString) throws SQLException {
        FriendInfo friendInfo;
        ResultSet result = naiveQuery(queryString);
        ArrayList<FriendInfo> friendInfoList = new ArrayList<>();
        while (result.next()) {
            friendInfo = new FriendInfo();
            friendInfo.user_id = result.getInt("user_id");
            friendInfo.username = result.getString("username");
            friendInfo.name = result.getString("name");
            friendInfo.gender = result.getString("gender");
            friendInfo.birthday = result.getDate("birthday");
            friendInfo.address = result.getString("address");
            friendInfoList.add(friendInfo);
        }
        return friendInfoList;
    }

    @Override
    public String toString() {
        return "{" + 
               "username='" + username + "'" + 
               ", name='" + name + "'" + 
               ", gender='" + gender + "'" + 
               ", birthday='" + birthday + "'" + 
               ", address='" + address + "'" + 
            "}";
    }
}