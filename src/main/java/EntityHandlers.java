import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.Model;
import database.entities.*;
import database.exceptions.InvalidDataException;

class EntityHandlers {
    private static User user;

    static void setUser(User user) {
        EntityHandlers.user = user;
    }
    
    static void handleInfo(String operation, String identity, List<Arg> args) {
        switch (operation) {
            case "update":
                Map<String, String> properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }
                try {
                    user.update(properties);
                    user.store();
                } catch (InvalidDataException e) {
                    App.println(e.getMessage());
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
            default:
                break;
        }
    }

    static void handleEducationEntity(String operation, String identity, List<Arg> args) {
        Education education;
        Date start, end;
        String level, school, degree;
        Map<String, String> properties;
        switch (operation) {
            case "add":
                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }
                try {
                    level = properties.get("level");
                    school = properties.get("school");
                    degree = properties.get("degree");
                    start = Model.parseDate(properties.get("start"));
                    end = Model.parseDate(properties.get("end"));
                    education = new Education(user.getUserID(), level, start, end, school, degree);
                    education.create();
                } catch (InvalidDataException e) {
                    App.println(e.getMessage());
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "update":
                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }

                try {
                    education = new Education(Integer.parseInt(identity));
                    education.load();
                    education.update(properties);
                    education.store();
                } catch (InvalidDataException e) {
                    App.println(e.getMessage());
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "remove":
                if (identity == null) {
                    App.println("Identity required!");
                    return;
                }

                try {
                    education = new Education(Integer.parseInt(identity));
                    education.delete();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    static void handleWorkEntity(String operation, String identity, List<Arg> args) {
        Work work;
        Date start, end;
        String employer, position;
        Map<String, String> properties;
        switch (operation) {
            case "add":
                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }
                try {
                    employer = properties.get("employer");
                    position = properties.get("position");
                    start = Model.parseDate(properties.get("start"));
                    end = Model.parseDate(properties.get("end"));
                    work = new Work(user.getUserID(), employer, start, end, position);
                    work.create();
                } catch (InvalidDataException e) {
                    App.println(e.getMessage());
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "update":
                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }

                try {
                    work = new Work(Integer.parseInt(identity));
                    work.load();
                    work.update(properties);
                    work.store();
                } catch (InvalidDataException e) {
                    App.println(e.getMessage());
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "remove":
                if (identity == null) {
                    App.println("Identity required!");
                    return;
                }

                try {
                    work = new Work(Integer.parseInt(identity));
                    work.delete();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    static void handleFriendEntity(String operation, String identity, List<Arg> args) {
        Friendship friendship;
        Integer friend_id;
        Map<String, String> properties;
        switch (operation) {
            case "add":
                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }
                try {
                    friend_id = Integer.parseInt(identity);
                    friendship = new Friendship(user.getUserID(), friend_id);
                    friendship.create();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "update":
                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }

                try {
                    friendship = new Friendship(user.getUserID(), Integer.parseInt(identity));
                    friendship.load();
                    friendship.update(properties);
                    friendship.store();
                } catch (InvalidDataException e) {
                    App.println(e.getMessage());
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "remove":
                if (identity == null) {
                    App.println("Identity required!");
                    return;
                }

                try {
                    friendship = new Friendship(user.getUserID(), Integer.parseInt(identity));
                    friendship.delete();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    static void handleFriendGroupEntity(String operation, String identity, List<Arg> args) {

    }

    static void handlePostEntity(String operation, String identity, List<Arg> args) {
        Post post;
        Boolean isPrivate = false;
        String content = null;
        Map<String, String> properties;
        switch (operation) {
            case "add":
                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }
                try {
                    isPrivate = Boolean.parseBoolean(properties.get("private"));
                    content = properties.get("content");
                    post = new Post(user.getUserID(), isPrivate, content);
                    post.create();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "update":
                if (identity == null) {
                    App.println("Identity required!");
                    return;
                }

                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }

                try {
                    post = new Post(Integer.parseInt(identity));
                    post.load();
                    post.update(properties);
                    post.store();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "remove":
                if (identity == null) {
                    App.println("Identity required!");
                    return;
                }

                try {
                    post = new Post(Integer.parseInt(identity));
                    post.delete();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    static void handleReplyEntity(String operation, String identity, List<Arg> args) {
        Reply reply;
        Integer post_id;
        String content = null;
        Map<String, String> properties;
        switch (operation) {
            case "add":
                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }
                try {
                    post_id = Integer.parseInt(properties.get("post_id"));
                    content = properties.get("content");
                    reply = new Reply(user.getUserID(), post_id, content);
                    reply.create();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "remove":
                if (identity == null) {
                    App.println("Identity required!");
                    return;
                }

                try {
                    reply = new Reply(Integer.parseInt(identity));
                    reply.delete();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    static void handleShareEntity(String operation, String identity, List<Arg> args) {
        Share share;
        Integer post_id;
        String content = null;
        Map<String, String> properties;
        switch (operation) {
            case "add":
                properties = new HashMap<>();
                for (Arg arg : args) {
                    properties.put(arg.getName(), arg.getValue());
                }
                try {
                    post_id = Integer.parseInt(properties.get("post_id"));
                    content = properties.get("content");
                    share = new Share(user.getUserID(), post_id, content);
                    share.create();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            case "remove":
                if (identity == null) {
                    App.println("Identity required!");
                    return;
                }

                try {
                    share = new Share(Integer.parseInt(identity));
                    share.delete();
                } catch (SQLException e) {
                    App.println(e.getMessage());
                }
                break;
            default:
                break;
        }
    }
}