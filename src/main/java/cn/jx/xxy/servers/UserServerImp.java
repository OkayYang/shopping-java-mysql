package cn.jx.xxy.servers;
import cn.jx.xxy.user.Register;
import cn.jx.xxy.user.RegisterException;
import cn.jx.xxy.user.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        }finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public User login(String email, String upd) {
        try {
            conn = Mysql.connectMysql();
            String sql = "select uid,uname,upd,usex,uage,uphone,ubalance from user where uname=? and upd = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,email);
            ps.setObject(2,upd);
            rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("登录成功，欢迎"+rs.getString("uname")+",账户余额:"+rs.getDouble("ubalance"));
                return new User(rs.getInt("uid"),rs.getString("uname"),rs.getString("upd"),rs.getString("usex"),
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
                throwables.printStackTrace();
            }

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
        }finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

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
        }finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return false;
    }

    @Override
    public boolean buyGoods(User user,String name, int num) {
        try {
            double price =0;
            int gid = 0;
            int stock = 0;
            rs = new GoodServerImp().searchGoods(name);
            while (rs.next()){
                gid = rs.getInt("id");
                price = rs.getDouble("price")*num;
                stock =rs.getInt("stock");
            }
            if (stock<num){
                System.out.println("商品库存不足！,当前库存:"+stock);
                return false;
            };
            if (num<=0){
                System.out.println("需要购买的数量必须大于0！");
                return false;
            }
            if (user.getBalance()<=price){
                System.out.println("您的余额不足！,当前余额:"+user.getBalance());
                return false;
            }

            conn.setAutoCommit(false);
            String sql1 = "update goods set stock = stock-? where gname = ?";
            ps = conn.prepareStatement(sql1);
            ps.setObject(1,num);
            ps.setObject(2,name);
            int status1 =ps.executeUpdate();

            String sql2 = "update user set ubalance = ubalance -? where uname = ?";
            ps = conn.prepareStatement(sql2);
            ps.setObject(1,price);
            ps.setObject(2,user.getUname());
            int status2 = ps.executeUpdate();
            if (status1==1 && status2==1){
                String sql3 = "insert into record(uid,gid,num,datetime) values(?,?,?,?)";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ps = conn.prepareStatement(sql3);
                ps.setObject(1,user.getUid());
                ps.setObject(2,gid);
                ps.setObject(3,num);
                ps.setObject(4,df.format(new Date()));
                if (ps.executeUpdate()==1) {
                    conn.commit();
                    System.out.println("购买成功");
                    return true;
                }

            }

        } catch (SQLException throwables) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }finally {
            try {
                rs.close();
                ps.close();
                conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean buyGoods(User user, int id, int num) {
        try {
            double price =0;
            int stock = 0;
            rs = new GoodServerImp().searchGoods(id);
            while (rs.next()){

                price = rs.getDouble("price")*num;
                stock =rs.getInt("stock");
            }

            if (stock<num){
                System.out.println("商品库存不足！,当前库存:"+stock);
                return false;
            };
            if (num<=0){
                System.out.println("需要购买的数量必须大于0!");
                return false;
            }
            if (user.getBalance()<=price){
                System.out.println("您的余额不足！,当前余额:"+user.getBalance());
                return false;
            }
            conn.setAutoCommit(false);
            String sql1 = "update goods set stock = stock-? where id = ?";
            ps = conn.prepareStatement(sql1);
            ps.setObject(1,num);
            ps.setObject(2,id);
            int status1 =ps.executeUpdate();

            String sql2 = "update user set ubalance = ubalance -? where uname = ?";
            ps = conn.prepareStatement(sql2);
            ps.setObject(1,price);
            ps.setObject(2,user.getUname());
            int status2 = ps.executeUpdate();
            if (status1==1 && status2==1){
                String sql3 = "insert into record(uid,gid,num,datetime) values(?,?,?,?)";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ps = conn.prepareStatement(sql3);
                ps.setObject(1,user.getUid());
                ps.setObject(2,id);
                ps.setObject(3,num);
                ps.setObject(4,df.format(new Date()));
                if (ps.executeUpdate()==1) {
                    conn.commit();
                    System.out.println("购买成功");
                    return true;
                }

            }

        } catch (SQLException throwables) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }finally {
            try {
                rs.close();
                ps.close();
                conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void showRecord(User user) {
        try {
            String sql = "select datetime,gid ,gname,price,num from record ,goods where uid = ? and record.gid = goods.id";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,user.getUid());
            rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("购买日期:"+rs.getString(1)+",商品编号:"+rs.getInt(2)+",商品名称:"+rs.getString(3)+
                        ",商品价格:"+rs.getDouble(4)+",购买数量:"+rs.getInt(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
