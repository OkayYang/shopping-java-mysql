package cn.jx.xxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private String uname;
    private String upd;
    private String sex;
    private int phone;
    private double balance;

    private static Connection conn = UseMysql.connectMysql();
    private static PreparedStatement ps;
    private static ResultSet rs;

    private User() {
    }

    private User(String uname, String upd){
        this.uname = uname;
        this.upd = upd;
    }

    public static User Register(){

        {
            System.out.println("-----------------邮箱注册-------------------");
        }
        try {
            Register people1 = new Register();
            Scanner s2 = new Scanner(System.in);
            System.out.print("邮箱:");
            String email = s2.next();

            String sql = "select uname from user where uname=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                throw  new RegisterException("该邮箱已经被注册");
            }
            people1.setMail(email);
            System.out.print("密码:");
            people1.setPassword(s2.next());

            String sql2 = "insert into user(uname,upd) values(?,?)";
            ps= conn.prepareStatement(sql2);
            ps.setObject(1,people1.getMail());
            ps.setObject(2,people1.getPassword());
            int n = ps.executeUpdate();
            if (n==1){
                System.out.println(people1);
                return  new User(people1.getMail(),people1.getPassword());
            }else {
                System.out.println("注册失败！");
            }

        }catch (SQLException throwables) {
            throwables = new SQLException("系统出现问题啦！");
            System.out.println(throwables.getMessage());
        } catch (RegisterException e) {
            System.out.println(e.getMessage());

        }finally {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables = new SQLException("数据库关闭异常！");
                System.out.println(throwables.getMessage());
            }
            UseMysql.closeMysql();
        }

        return null;
    }


    public static void login(String uname,String upd){

        String sql = "select uname from user where uname=? and upd = ?";

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
            System.out.println(throwables.getMessage());
        }finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException throwables) {
                throwables = new SQLException("数据库关闭异常！");
                System.out.println(throwables.getMessage());
            }
            UseMysql.closeMysql();
        }
    }

}
