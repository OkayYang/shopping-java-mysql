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
    private static User instance;

    private User() {
    }

    private User(String uname, String upd){
        this.uname = uname;
        this.upd = upd;
    }
    public static User getInstance(){
        if (instance==null) instance = new User();
        return instance;
    }
    public static void Register(){
        {
            System.out.println("-----------------邮箱注册-------------------");
        }
        Register people1 = new Register();
        Scanner s2 = new Scanner(System.in);
        try {
            System.out.print("邮箱:");
            people1.setMail(s2.next());
            System.out.print("密码:");
            people1.setPassword(s2.next());
            Connection conn = UseMysql.connectMysql();
            PreparedStatement ps = null;
            ResultSet rs = null;
            String sql = "insert into user(uname,upd) values(?,?)";
            String sql2 = "select uname from user where uname=?";

            try {
                ps = conn.prepareStatement(sql2);
                ps.setObject(1,people1.getMail());
                /*ps.setObject(2,people1.getPassword());*/
                rs= ps.executeQuery();
                if (rs.next()){
                    System.out.println("该账号已经被注册！！");
                }else {
                    ps= conn.prepareStatement(sql);
                    ps.setObject(1,people1.getMail());
                    ps.setObject(2,people1.getPassword());
                    int n = ps.executeUpdate();
                    if (n==1){
                        System.out.println(people1);
                        if (instance==null){
                            instance = new User(people1.getMail(),people1.getPassword());
                        }

                    }else {
                        System.out.println("注册失败！");
                    }
                }

            } catch (SQLException throwables) {
                throwables = new SQLException("更新sql语句有误！");
                throwables.printStackTrace();
            }finally {
                try {
                    ps.close();
                } catch (SQLException throwables) {
                    throwables = new SQLException("数据库关闭异常！");
                    throwables.printStackTrace();
                }
                UseMysql.closeMysql();
            }
        } catch (RegisterException e) {
            System.out.println(e.getMessage());
        }

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
