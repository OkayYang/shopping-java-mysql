package cn.jx.xxy.servers;

import java.sql.Connection;
import java.sql.ResultSet;

public interface GoodServer {
    boolean showStore();
    ResultSet searchGoods(String name);
    boolean updateStock(Connection cnn,String name, int num);
}
