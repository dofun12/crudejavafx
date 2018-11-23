package com.lemanoman.javafxmaven;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteJDBCDriverConnection {
    /**
     * Connect to a sample database
     */
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed() && connection.isValid(6000)) {
            return connection;
        } else {
            this.connection = connect();
            return this.connection;
        }
    }

    public static Connection connect() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:crude.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return c;
    }

    public void createDefaultTable() {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                Statement statement = conn.createStatement();
                statement.executeUpdate("" +
                        "CREATE TABLE IF NOT EXISTS user (\n" +
                        " id INTEGER PRIMARY KEY,\n" +
                        " name TEXT NOT NULL\n" +
                        ");");
                statement.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addUser(String name) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                Statement statement = conn.createStatement();
                statement.executeUpdate("insert into user(name) values ('" + name + "')");
                statement.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteUser(Integer id) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                Statement statement = conn.createStatement();
                statement.executeUpdate("delete from user where id = "+id);
                statement.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateUser(String name,Integer id) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                Statement statement = conn.createStatement();
                statement.executeUpdate("update user set name = '"+name+"' where id = "+id);
                statement.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Map<String, Object>> getListUser() {
        List<Map<String,Object>> list = null;
        try {
            Connection conn = getConnection();
            if (conn != null) {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from user");
                list = extractMap(rs);
                rs.close();
                statement.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private List<Map<String, Object>> extractMap(ResultSet rs) throws SQLException {
        List<Map<String, Object>> listObject = new ArrayList<>();
        ResultSetMetaData metadata = rs.getMetaData();
        while (rs.next()) {
            Map<String,Object> rowmap = new HashMap<>();
            for (int c = 1; c <= metadata.getColumnCount(); c++) {
                rowmap.put(metadata.getColumnName(c),rs.getObject(c));
            }
            listObject.add(rowmap);
        }
        return listObject;
    }
}