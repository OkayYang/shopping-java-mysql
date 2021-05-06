package cn.jx.xxy.servers;



import cn.jx.xxy.user.Register;
import cn.jx.xxy.user.RegisterException;
import cn.jx.xxy.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServerImp implements UserServer{
    private static Connection conn = Mysql.connectMysql() ;
    private static PreparedStatement ps;
    private static ResultSet rs;
    @Override
    public User register(String email, String upd) {
        try {
            Register people = new Register();
            String sql = "select uname from user where uname=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                throw  new RegisterException("该邮箱已经被注册");
            }
            people.setMail(email);
            people.setPassword(upd);

            String sql2 = "insert into user(uname,upd) values(?,?)";
            ps= conn.prepareStatement(sql2);
            ps.setObject(1,people.getMail());
            ps.setObject(2,people.getPassword());
            int n = ps.executeUpdate();
            if (n==1){
                System.out.println(people);
                return  new User(people.getMail(),people.getPassword());
            }else {
                System.out.println("注册失败！");
            }

        }catch (SQLException throwables) {
            throwables = new SQLException("系统出现问题啦！");
            System.out.println(throwables.getMessage());
        } catch (RegisterException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    @Override
    public User login(String email, String upd) {
        try {
            conn = Mysql.connectMysql();
            String sql = "select uname,upd,usex,uage,uphone,ubalance from user where uname=? and upd = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,email);
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
        }
        return null;
    }

    @Override
    public boolean updatePd(User user,String upd) {
        try{
            if (!Register.judgePd(upd)) {
                throw new RegisterException("密码格式错误密码长度6-16位至少包含数字和字母!!!!!");
            }
            String sql = "update user set upd = ? where uname =? and upd = ? ";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,upd);
            ps.setObject(2,user.getUname());
            ps.setObject(3,user.getUpd());
            if (ps.executeUpdate()==1) {
                user.setUpd(upd);
                System.out.println("修改成功！");
                return true;
            }else {
                throw new SQLException();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (RegisterException re){
            System.out.println(re.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateInfo(User user, String sex, int age, long phone, double balance) {
        try{

            String sql = "update user set usex = ?,uage = ? ,uphone = ? , ubalance = ? where uname =? and upd = ? ";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,sex);
            ps.setObject(2,age);
            ps.setObject(3,phone);
            ps.setObject(4,balance);
            ps.setObject(5,user.getUname());
            ps.setObject(6,user.getUpd());

            if (ps.executeUpdate()==1) {
                user.setSex(sex);
                user.setPhone(phone);
                user.setBalance(balance);
                user.setAge(age);
                System.out.println("用户信息更新成功！");
                return true;
            }else {
                throw new SQLException();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}
