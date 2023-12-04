/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tap_u3tv.classes;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author angel
 */
public class DBManager {

    private static Connection connection;
    private static Statement statement;

    public DBManager() {
    }

    public static void open() throws Exception {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tapdb_u3tv"  , "root", "2003");
        statement = connection.createStatement();
    }

    public static void close() throws Exception {
        statement.close();
        connection.close();
    }

    public static ArrayList<User> getUsers() throws Exception {
        open();
        ResultSet rs = statement.executeQuery("SELECT * FROM users");
        ArrayList<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(7), rs.getBoolean(5), rs.getBoolean(6));
            users.add(user);
        }

        rs.close();
        close();
        return users;
    }

    public static int createUser(String name, String email, String password) throws Exception {
        open();

        //String pfpPath = Paths.get("C:\\Users\\angel\\Pictures\\Therum\\pfp-default.png").toAbsolutePath().toString();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, email, password, pfp_path, isAsmin, isActive) VALUES(?, ?, ?, ?, 0, 1 )");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, Paths.get("C:\\Users\\Jasie\\OneDrive\\Escritorio\\TapProject\\pfp-default.png").toAbsolutePath().toString());
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static int updateUserPhoto(String path, int id) throws Exception {
        open();
        int results = statement.executeUpdate("UPDATE users SET pfp_path = '" + path + "' WHERE id=" + id);

        close();
        return results;
    }

    public static int countUserPost(int id) throws Exception {
        open();

        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM posts WHERE Users_idUsers = " + id);
        rs.next();
        int results = rs.getInt(1);

        rs.close();
        close();
        return results;
    }

    public static int countUserComments(int id) throws Exception {
        open();

        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM comments WHERE Users_idUsers = " + id);
        rs.next();
        int results = rs.getInt(1);

        rs.close();
        close();
        return results;
    }

    public static int countUserLikes(int id) throws Exception {
        open();

        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM likescomentario WHERE Users_idUsers = " + id);
        rs.next();
        int results = rs.getInt(1);
        rs = statement.executeQuery("SELECT COUNT(*) FROM likepost WHERE Users_idUsers = " + id);
        rs.next();
        results += rs.getInt(1);

        rs.close();
        close();
        return results;
    }

    public static int checkUser(int id) throws Exception {
        open();
        ResultSet rs = statement.executeQuery("SELECT isActive FROM users WHERE id =" + id);
        rs.next();
        int results = rs.getInt(1);

        rs.close();
        close();
        return results;
    }

    public static int deactivateUser(User user) throws Exception {
        open();
        int results = statement.executeUpdate("UPDATE users SET isActive = 0 WHERE id=" + user.getId());
        close();
        return results;
    }

    public static int activeuser(int id) throws Exception {
        open();
        int results = statement.executeUpdate("UPDATE users SET isActive = 1 WHERE id=" + id);
        close();
        return results;
    }

    public static int createPost(int userId, String content, String date) throws Exception {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO posts(content, created_at, Users_idUsers) VALUES(?, ?, ?)");
        preparedStatement.setString(1, content);
        preparedStatement.setString(2, date);
        preparedStatement.setInt(3, userId);
        int results = preparedStatement.executeUpdate();

        close();
        return results;

    }

    public static int countPostComments(int id) throws Exception {
        open();

        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM comments WHERE Posts_idPosts = " + id);
        rs.next();
        int results = rs.getInt(1);

        rs.close();
        close();
        return results;
    }

    public static int countPostLikes(int id) throws Exception {
        open();

        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM likepost WHERE Posts_idPosts = " + id);
        rs.next();
        int results = rs.getInt(1);

        rs.close();
        close();
        return results;
    }

    public static int deletePost(int id) throws Exception {
        deleteAllLikesFromPost(id);
        deleteAllCommentsFromPost(id);

        open();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM posts WHERE idPosts = ?");
        preparedStatement.setInt(1, id);
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static int deleteAllLikesFromPost(int postId) throws Exception {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM likepost WHERE Posts_idPosts = ?");
        preparedStatement.setInt(1, postId);
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static int deleteAllCommentsFromPost(int id) throws Exception {
        deleteAllLikesFromComment(id);

        open();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM comments WHERE Posts_idPosts = ?");
        preparedStatement.setInt(1, id);
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static int countPostLikeFromUser(int idPost, int idUser) throws Exception {
        open();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM likepost WHERE Posts_idPosts = ? AND Users_idUsers = ?");
        preparedStatement.setInt(1, idPost);
        preparedStatement.setInt(2, idUser);

        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        int results = rs.getInt(1);

        rs.close();
        close();
        return results;
    }

    public static int createLikeOnPost(int themeId, int userId) throws Exception {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  likepost(Users_idUsers, Posts_idPosts) VALUES(?, ?)");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, themeId);
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static int deleteLikeOnPost(int themeId, int userId) throws Exception {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM likepost WHERE Users_idUsers = ? AND Posts_idPosts = ?");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, themeId);
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static ArrayList<Theme> readThemes() throws Exception {
        open();
        ResultSet rs = statement.executeQuery("SELECT * FROM posts");
        ArrayList<Theme> themes = new ArrayList<>();

        while (rs.next()) {
            Theme theme = new Theme(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getInt(3));
            themes.add(theme);
        }

        rs.close();
        close();
        return themes;
    }
    
    public static ArrayList<Theme> search(String query) throws Exception {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM posts WHERE content LIKE ?");
        preparedStatement.setString(1, "%" + query + "%");
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Theme> themes = new ArrayList<>();
        
        while (rs.next()) {
            Theme theme = new Theme(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getInt(3));
            themes.add(theme);
        }
        
        rs.close();
        close();
        return themes;
    }

    public static int createComment(int userId, int postId, String content, String date) throws Exception {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO comments(content, created_at, Users_idUsers, Posts_idPosts) VALUES(?, ?, ?, ?)");
        preparedStatement.setString(1, content);
        preparedStatement.setString(2, date);
        preparedStatement.setInt(3, userId);
        preparedStatement.setInt(4, postId);
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static ArrayList<Comment> readCommentsFromPost(int id) throws Exception {
        open();
        ResultSet rs = statement.executeQuery("SELECT * FROM comments WHERE Posts_idPosts =" + id);
        ArrayList<Comment> comments = new ArrayList<>();

        while (rs.next()) {
            Comment comment = new Comment(rs.getInt(1), rs.getString(2), rs.getString(5), rs.getInt(3), rs.getInt(4));
            comments.add(comment);
        }

        rs.close();
        close();
        return comments;

    }

    public static int countCommentLikesFromUser(int idPost, int idUser) throws Exception {
        open();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM likescomentario WHERE Comments_idComments = ? AND Users_idUsers = ?");
        preparedStatement.setInt(1, idPost);
        preparedStatement.setInt(2, idUser);

        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        int results = rs.getInt(1);

        rs.close();
        close();
        return results;
    }

    public static int countCommentLikes(int id) throws Exception {
        open();

        ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM likescomentario WHERE Comments_idComments = " + id);
        rs.next();
        int results = rs.getInt(1);

        rs.close();
        close();
        return results;
    }

    public static int createLikeOnComment(int commentId, int userId) throws Exception {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  likescomentario(Users_idUsers, Comments_idComments) VALUES(?, ?)");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, commentId);
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static int deleteComment(int id) throws Exception {
        deleteAllLikesFromComment(id);

        open();

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM comments WHERE idCommets = ?");
        preparedStatement.setInt(1, id);
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static int deleteLikeOnComment(int commentId, int userId) throws Exception {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM likescomentario WHERE Users_idUsers = ? AND Comments_idComments = ?");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, commentId);
        int results = preparedStatement.executeUpdate();

        close();
        return results;
    }

    public static int deleteAllLikesFromComment(int id) throws Exception {
        ArrayList<Integer> idList = commentsIdList(id);
        int results = 0;

        for (Integer commentId : idList) {
            open();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM likescomentario WHERE Comments_idComments = ?");
            preparedStatement.setInt(1, commentId);
            results = preparedStatement.executeUpdate();
            
            close();
        }

        close();
        return results;
    }

    private static ArrayList<Integer> commentsIdList(int postId) throws Exception {
        open();
        ResultSet rs = statement.executeQuery("SELECT * FROM comments WHERE Posts_idPosts = " + postId);
        ArrayList<Integer> idList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            System.out.println(id);
            idList.add(id);
        }

        rs.close();
        close();
        return idList;
    }
}
