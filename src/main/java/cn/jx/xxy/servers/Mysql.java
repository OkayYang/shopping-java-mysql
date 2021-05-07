package cn.jx.xxy.servers;

import java.sql.*;
import java.util.ResourceBundle;

public class Mysql{
    private static ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
    private final static String URL = bundle.getString("url");
    private final static String USER = bundle.getString("user");
    private final static String PD = bundle.getString("pd");
    private static Connection conn;

    public static Connection connectMysql(){
        if (conn==null){
            try {

                conn = DriverManager.getConnection(URL,USER,PD);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return conn;

    }
    public static void closeMysql(){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables = new SQLException("数据库关闭失败!");
            System.out.println(throwables.getMessage());
        }
    }
}
