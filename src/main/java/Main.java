import cn.jx.xxy.user.User;

public class Main {
    public static void main(String[] args) {

        User user2 = User.login("168@qq.com","yang121231");
        if (user2 !=null){
            System.out.println(user2.updateInfo("男", 25, 18888888888L, 25600));
        }
    }

}
