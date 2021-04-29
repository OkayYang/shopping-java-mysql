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
    private int age;
    private long phone;
    private double balance;

    private static Connection conn ;
    private static PreparedStatement ps;
    private static ResultSet rs;

    private User(String uname, String upd){
        this.uname = uname;
        this.upd = upd;
    }

    public User(String uname, String upd, String sex, int age, long phone, double balance) {
        this.uname = uname;
        this.upd = upd;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.balance = balance;
    }



    public void setUpd(String upd) {
        try{
            if (!Register.judgePd(upd)) {
                throw new RegisterException("密码格式错误密码长度6-16位至少包含数字和字母!!!!!");
            }
            conn = UseMysql.connectMysql();
            String sql = "update user set upd = ? where uname =? and upd = ? ";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,upd);
            ps.setObject(2,this.uname);
            ps.setObject(3,this.upd);
            if (ps.executeUpdate()==1) {
                this.upd = upd;
                System.out.println("修改成功！");
            }else {
                throw new SQLException();
            }

        } catch (SQLException throwables) {
            throwables = new SQLException("修改密码失败");
            throwables.printStackTrace();
        }catch (RegisterException re){
            System.out.println(re.getMessage());
        }
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static User Register(String email , String upd){

        try {
            conn = UseMysql.connectMysql();
            Register people1 = new Register();
            String sql = "select uname from user where uname=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                throw  new RegisterException("该邮箱已经被注册");
            }
            people1.setMail(email);
            people1.setPassword(upd);

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


    public static User login(String uname,String upd){
        try {
            conn = UseMysql.connectMysql();
            String sql = "select uname,upd,usex,uage,uphone,ubalance from user where uname=? and upd = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,uname);
            ps.setObject(2,upd);
            rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("登录成功，欢迎"+rs.getString("uname"));
                return new User(rs.getString("uname"),rs.getString("upd"),rs.getString("usex"),
                        rs.getInt("uage"),
                        rs.getLong("uphone"),rs.getDouble("ubalance"));
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
        return null;
    }

    @Override
    public String toString() {
        return "用户:"+this.uname+",性别:"+this.sex+",余额:"+this.balance;
    }
}
