package cn.jx.xxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String uname;
    private String upd;
    private String sex;
    private int phone;
    private double balance;
    private User(String uname,String upd){
        this.uname = uname;
        this.upd = upd;
    }

    public static User Register(String uname,String upd){
        return new User(uname,upd);
    }
    public static void login(String uname,String upd){
        Connection conn= UseMysql.connectMysql();
        String sql = "select uname from user where uname=? and upd = ?";
        PreparedStatement ps= null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1,uname);
            ps.setObject(2,upd);
            rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("登录成功，欢迎"+rs.getString("uname"));
            }else {
                System.out.println("账号或密码错误！");
            }

        } catch (SQLException throwables) {
            throwables = new SQLException("查询sql语句有误！");
            throwables.printStackTrace();
        }finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException throwables) {
                throwables = new SQLException("数据库关闭异常！");
                throwables.printStackTrace();
            }
            UseMysql.closeMysql();
        }
    }

}
