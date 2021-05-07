package cn.jx.xxy.servers;

import java.sql.*;

public class Mysql{
    private final static String URL = "jdbc:mysql://localhost:3306/supermarket?useSSL=false";
    private final static String USER = "root";
    private final static String PD = "yang611612";
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
