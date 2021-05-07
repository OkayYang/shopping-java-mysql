package cn.jx.xxy.store;

import cn.jx.xxy.servers.GoodServer;
import cn.jx.xxy.servers.GoodServerImp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Goods {
    private String name;
    private double bid;
    private double price;
    private int stock;
    private static final GoodServer GS= new GoodServerImp();
    public static boolean goodsInfo(){
        return GS.showStore();
    }
    public static boolean searchGoods(String name){
        try {
            ResultSet rs =GS.searchGoods(name);
            while (rs.next()){
                System.out.println("商品编号:"+rs.getInt("id")+",商品名称:"+rs.getString("gname")+
                        ",商品价格:"+rs.getDouble("price")+",商品库存:"+rs.getInt("stock"));
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;


    }


}
