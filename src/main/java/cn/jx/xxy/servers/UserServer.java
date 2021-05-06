package cn.jx.xxy.servers;

import cn.jx.xxy.user.User;

public interface UserServer{

    User register(String email , String upd);
    User login(String email , String upd);
    boolean updatePd(User user,String upd);
    boolean updateInfo(User user,String sex,int age,long phone,double balance);
}
