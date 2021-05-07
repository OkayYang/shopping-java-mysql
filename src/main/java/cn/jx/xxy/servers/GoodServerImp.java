package cn.jx.xxy.servers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodServerImp implements GoodServer{
    private static Connection conn = Mysql.connectMysql();
    private static PreparedStatement ps;
    private static ResultSet rs;
    @Override
    public boolean showStore() {
        try {
            String sql = "select id,gname,price,stock from goods";
            ps = conn.prepareStatement(sql);

            rs= ps.executeQuery();
            while (rs.next()){
                System.out.println("商品编号:"+rs.getInt("id")+",商品名称:"+rs.getString("gname")+
                        ",商品价格:"+rs.getDouble("price")+",商品库存:"+rs.getInt("stock"));

            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public  ResultSet searchGoods(String name) {
        try {
            String sql = "select id,gname,price,stock from goods where gname = ?";
            ps= conn.prepareStatement(sql);
            ps.setObject(1,name);
            return  ps.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet searchGoods(int num) {
        try {
            String sql = "select id,gname,price,stock from goods where id = ?";
            ps= conn.prepareStatement(sql);
            ps.setObject(1,num);
            return  ps.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public  boolean updateStock(Connection conn,String name, int num) {
        try {
            String sql = "update goods set stock = stock-? where gname = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,num);
            ps.setObject(2,name);
            if (ps.executeUpdate()==1){
                System.out.println("购买成功");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
