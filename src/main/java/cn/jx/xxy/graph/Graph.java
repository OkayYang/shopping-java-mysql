package cn.jx.xxy.graph;

import cn.jx.xxy.servers.Mysql;
import cn.jx.xxy.store.Goods;
import cn.jx.xxy.user.User;

import java.util.Scanner;

public class Graph {
    private static Scanner s = new Scanner(System.in);
    public static void init(){
        while (true){
            index();
            System.out.print("请输入操作的选项:");
            int num = s.nextInt();
            switch (num){
                case 1:index1();break;
                case 2:index2();break;
                case 3:System.exit(0);
            }
        }
    }

    private static void index2() {
        System.out.println("----------用户注册----------");
        System.out.print("请输入要注册的用户名:");
        String uname = s.next();
        System.out.print("请输入密码:");
        String upd =s.next();
        User user = User.register(uname,upd);
        if (user!=null){
            System.out.println(user);
            System.out.println("---------初始化用户信息---------");
            System.out.print("请输入性别:");
            String sex = s.next();
            System.out.print("请输入年龄:");
            int age = s.nextInt();
            System.out.print("请输入电话号码:");
            long phone = s.nextLong();
            System.out.print("请输入充值金额:");
            double balance =s.nextDouble();
            if (user.updateInfo(sex,age,phone,balance)) {
                System.out.println("请重新登录！");
            }

        }
    }

    private static void index(){
        System.out.println("------------欢迎购物--------------");
        System.out.println("|                               |");
        System.out.println("|           1.登录               |");
        System.out.println("|           2.注册               |");
        System.out.println("|           3.退出               |");
        System.out.println("|                               |");
        System.out.println("--------------------------------- ");

    }

    private static void index1(){
        Scanner s = new Scanner(System.in);
        System.out.print("请输入用户名:");
        String name = s.next();
        System.out.print("请输入密码:");
        String pwd = s.next();
        User user = User.login(name,pwd);
        if (user!=null) {
            while (true){
                System.out.println("---------------个人中心--------------");
                System.out.println("|                                 |");
                System.out.println("|             1.开始购物            |");
                System.out.println("|             2.购买记录            |");
                System.out.println("|             3.退出               |");
                System.out.println("|                                 |");
                System.out.println("----------------------------------- ");
                System.out.print("请输入操作的选项:");
                int num = s.nextInt();
                if (num==3){
                    Mysql.closeMysql();
                    break;
                }
                switch (num){
                    case 1:
                        if (Goods.goodsInfo()){
                            System.out.print("请输入购买的商品名称或编号:");
                            String gName = s.next();
                            System.out.print("请输入购买数量:");
                            int gNum = s.nextInt();
                            boolean flag=false;
                            try {
                                int gid = Integer.valueOf(gName);
                                flag=user.buyGoods(gid,gNum);
                            }catch (Exception e){
                                flag=user.buyGoods(gName,gNum);
                            }
                            if (!flag) {
                                System.out.println("购买失败！");
                            }
                        }else System.out.println("显示商品失败！");
                        break;
                    case 2:
                        System.out.println("正在打印购买记录，请稍等.....");
                        user.showRecord();break;
                    default:break;
                }

            }

        }

    }


}
