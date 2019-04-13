import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.entities.User;
import database.entities.Education;
import database.entities.FriendGroup;
import database.entities.Work;
import database.views.*;

class ViewHandlers {
    private static User user;

    static void setUser(User user) {
        ViewHandlers.user = user;
    }

    static void handleEducationView(String identity, List<Arg> args) {
        StringBuilder query = new StringBuilder("SELECT * FROM education WHERE `user_id`=");
        query.append(user.getUserID().toString());
        if (identity != null) {
            query.append(" AND `education_id`=" + identity);
        }
        for (Arg arg : args) {
            switch (arg.getName()) {
                case "level":
                    query.append(" AND `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                case "school":
                    query.append(" AND `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                case "degree":
                    query.append(" AND `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                default:
                    break;
            }
        }
        query.append(";");
        try {
            ArrayList<Education> list = Education.query(query.toString());
            for (Education e : list) {
                App.println(e.toString());
            }
        } catch (SQLException e) {
            App.println(e.getMessage());
        }
    }

    static void handleWorkView(String identity, List<Arg> args) {
        StringBuilder query = new StringBuilder("SELECT * FROM work WHERE `user_id`=");
        query.append(user.getUserID().toString());
        if (identity != null) {
            query.append(" AND `work_id`=" + identity);
        }
        for (Arg arg : args) {
            switch (arg.getName()) {
                case "employer":
                    query.append(" AND `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                case "position":
                    query.append(" AND `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                default:
                    break;
            }
        }
        query.append(";");
        try {
            ArrayList<Work> list = Work.query(query.toString());
            for (Work e : list) {
                App.println(e.toString());
            }
        } catch (SQLException e) {
            App.println(e.getMessage());
        }
    }

    static void handleUserView(String identity, List<Arg> args) {

    }

    static void handleFriendView(String identity, List<Arg> args) {
        StringBuilder query = new StringBuilder("SELECT * FROM friend WHERE `user_id`=");
        query.append(user.getUserID().toString());
        if (identity != null) {
            query.append(" AND `friend_id`=" + identity);
        }
        for (Arg arg : args) {
            switch (arg.getName()) {
                case "group_id":
                    query.append(" AND `" + arg.getName() + "`" + arg.getOp() + arg.getValue());
                    break;
                case "username":
                    query.append(" AND `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                case "name":
                    query.append(" AND `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
            }
        }
        query.append(";");
        try {
            ArrayList<Friend> list = Friend.query(query.toString());
            for (Friend e : list) {
                App.println(e.toString());
            }
        } catch (SQLException e) {
            App.println(e.getMessage());
        }
    }

    static void handleFriendGroupView(String identity, List<Arg> args) {
        StringBuilder query = new StringBuilder("SELECT * FROM friend_group WHERE `user_id`=");
        query.append(user.getUserID().toString());
        if (identity != null) {
            query.append(" AND `group_id`=" + identity);
        }
        for (Arg arg : args) {
            switch (arg.getName()) {
                case "group_name":
                    query.append(" AND `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                default:
                    break;
            }
        }
        query.append(";");
        try {
            ArrayList<FriendGroup> list = FriendGroup.query(query.toString());
            for (FriendGroup e : list) {
                App.println(e.toString());
            }
        } catch (SQLException e) {
            App.println(e.getMessage());
        }
    }

    static void handlePostView(String identity, List<Arg> args) {
        boolean appended = false;
        StringBuilder query = new StringBuilder("SELECT * FROM post_info");
        for (Arg arg : args) {
            switch (arg.getName()) {
                case "post_id":
                    if (appended) {
                        query.append(" AND");
                    }
                    query.append(" `" + arg.getName() + "`" + arg.getOp() + arg.getValue());
                    break;
                case "username":
                    if (appended) {
                        query.append(" AND");
                    }
                    query.append(" `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                default:
                    break;
            }
        }
        query.append(";");
        try {
            ArrayList<PostInfo> list = PostInfo.query(query.toString());
            for (PostInfo e : list) {
                App.println(e.toString());
            }
        } catch (SQLException e) {
            App.println(e.getMessage());
        }
    }

    static void handleReplyView(String identity, List<Arg> args) {
        boolean appended = false;
        StringBuilder query = new StringBuilder("SELECT * FROM reply_info");
        for (Arg arg : args) {
            switch (arg.getName()) {
                case "post_id":
                    if (appended) {
                        query.append(" AND");
                    }
                    query.append(" `" + arg.getName() + "`" + arg.getOp() + arg.getValue());
                    break;
                case "reply_id":
                    if (appended) {
                        query.append(" AND");
                    }
                    query.append(" `" + arg.getName() + "`" + arg.getOp() + arg.getValue());
                    break;
                case "replied_id":
                    if (appended) {
                        query.append(" AND");
                    }
                    query.append(" `" + arg.getName() + "`" + arg.getOp() + arg.getValue());
                    break;
                case "username":
                    if (appended) {
                        query.append(" AND");
                    }
                    query.append(" `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                default:
                    break;
            }
        }
        query.append(";");
        try {
            ArrayList<ReplyInfo> list = ReplyInfo.query(query.toString());
            for (ReplyInfo e : list) {
                App.println(e.toString());
            }
        } catch (SQLException e) {
            App.println(e.getMessage());
        }
    }

    static void handleShareView(String identity, List<Arg> args) {
        boolean appended = false;
        StringBuilder query = new StringBuilder("SELECT * FROM share_info");
        for (Arg arg : args) {
            switch (arg.getName()) {
                case "post_id":
                    if (appended) {
                        query.append(" AND");
                    }
                    query.append(" `" + arg.getName() + "`" + arg.getOp() + arg.getValue());
                    break;
                case "share_id":
                    if (appended) {
                        query.append(" AND");
                    }
                    query.append(" `" + arg.getName() + "`" + arg.getOp() + arg.getValue());
                    break;
                case "username":
                    if (appended) {
                        query.append(" AND");
                    }
                    query.append(" `" + arg.getName() + "`" + arg.getOp() + "'" + arg.getValue() + "'");
                    break;
                default:
                    break;
            }
        }
        query.append(";");
        try {
            ArrayList<ShareInfo> list = ShareInfo.query(query.toString());
            for (ShareInfo e : list) {
                App.println(e.toString());
            }
        } catch (SQLException e) {
            App.println(e.getMessage());
        }
    }
}