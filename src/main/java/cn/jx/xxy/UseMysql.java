package cn.jx.xxy;

import java.sql.*;

public class UseMysql{
    private final static String URL = "jdbc:mysql://localhost:3306/supermarket?useSSL=false";
    private final static String USER = "root";
    private final static String PD = "yang611612";
    private static Connection conn;

    public static Connection connectMysql(){
        try {
            conn = DriverManager.getConnection(URL,USER,PD);

        } catch (SQLException throwables) {
            throwables = new SQLException("数据库连接失败!");
            throwables.printStackTrace();
            System.exit(0);
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
