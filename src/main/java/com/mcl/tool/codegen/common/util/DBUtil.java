package com.mcl.tool.codegen.common.util;

import java.sql.*;
public class DBUtil {

    // 数据库链接信息
    private static String user = "root";
    private static String password = "Blsx2018";
    private static String url = "jdbc:mysql://192.168.2.233:3306/INFORMATION_SCHEMA?characterEncoding=UTF-8&characterSetResults=UTF-8&zeroDateTimeBehavior=convertToNull";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    // 事务配置
    private static final boolean AUTOCOMMIT = false;    // 是否自动提交事务

    static {
        try {
            user = PropertiesUtil.properties.getProperty("spring.datasource.username");
            password = PropertiesUtil.properties.getProperty("spring.datasource.password");
            url = PropertiesUtil.properties.getProperty("spring.datasource.url");
            driver = PropertiesUtil.properties.getProperty("spring.datasource.driver-class-name");
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            conn.setAutoCommit(AUTOCOMMIT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取Statement
     * @param conn
     * @return
     */
    public static Statement getStatement(Connection conn){
        Statement statement = null;

        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statement;
    }
    /**
     * 获取 PreparedStatement
     * @param conn
     * @param sql
     * @return
     */
    public static PreparedStatement getPreparedStatement(Connection conn,String sql){
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    /** 
     * 关闭资源
     * @param conn
     * @param statement
     */
    public static void closeAll(Connection conn,Statement statement){
        try {
            if(conn!=null)conn.close();
            if(statement!=null)statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 关闭资源
     * @param conn
     * @param statement
     * @param resultSet
     */
    public static void closeAll(Connection conn,Statement statement,ResultSet resultSet){
        try {
            if(conn!=null)conn.close();
            if(statement!=null)statement.close();
            if(resultSet!=null)resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 事务回滚
     * @param conn
     */
    public static void rollBack(Connection conn){
        try {
            conn.rollback();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}