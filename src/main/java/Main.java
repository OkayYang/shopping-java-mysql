import cn.jx.xxy.User;

public class Main {
    public static void main(String[] args) {

        User user1 =User.Register("168@qq.com","yang121231");
        System.out.println(user1);
        User user2 = User.login("yang1@qq.com","yang521");
        if (user2 !=null){
            System.out.println(user2);
            user2.setUpd("yang52199");
        }
    }

}
