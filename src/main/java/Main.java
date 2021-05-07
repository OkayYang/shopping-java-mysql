import cn.jx.xxy.servers.GoodServerImp;
import cn.jx.xxy.store.Goods;
import cn.jx.xxy.user.User;

public class Main {
    public static void main(String[] args) {
        User user = User.login("1347456958@qq.com","yang52199");
        if (user!=null){
            user.buyGoods("可乐",65);
            user.showRecord();
        }


    }

}
