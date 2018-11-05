package com.niu.top.redisdemo.mysql;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.*;

/**
 * @author hongwei
 * @date 2018/11/2 15:52
 */
public class DBUtils {

    private static Connection connection;
    private static Statement statement;

    public static Connection getConnection(){
        if(connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://120.79.237.138:3306/userdb", "root", "1Q2w3e4r!");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static Statement getStatement(){
        if(statement == null) {
            connection = getConnection();
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statement;
    }

    public static int insertLock(){
        Statement statement = getStatement();
        int result;
        try {
            result = statement.executeUpdate(" insert into t_lock values (1) ");
        } catch (SQLException e) {
            result = 0;
        }/*finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
        return result;
    }

    public static int deleteLock(){
        Statement statement = getStatement();
        int result;
        try {
            result = statement.executeUpdate(" delete from t_lock ");
        } catch (SQLException e) {
            result = 0;
        }/*finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
        return result;
    }
    public static void main(String[] args) {
      //  insertLock();
        deleteLock();
    }
}
