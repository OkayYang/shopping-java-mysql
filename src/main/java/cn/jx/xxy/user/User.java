package cn.jx.xxy.user;

import cn.jx.xxy.servers.UserServer;
import cn.jx.xxy.servers.UserServerImp;

public class User {
    private int uid;
    private String uname;
    private String upd;
    private String sex;
    private int age;
    private long phone;
    private double balance;
    private static final UserServer US = new UserServerImp();

    public User(String uname, String upd){
        this.uname = uname;
        this.upd = upd;
    }

    public User(int uid,String uname, String upd, String sex, int age, long phone, double balance) {
        this.uid = uid;
        this.uname = uname;
        this.upd = upd;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUname() {
        return uname;
    }

    public String getUpd() {
        return upd;
    }

    public static User register(String email , String upd){

        return US.register(email,upd);

    }

    public static User login(String email,String upd){
        return US.login(email,upd);
    }

    public boolean updatePd(String upd){
        return US.updatePd(this,upd);
    }
    public boolean updateInfo(String sex, int age, long phone, double balance){
        return US.updateInfo(this,sex,age,phone,balance);
    }
    public boolean buyGoods(String name,int num){
        return US.buyGoods(this,name,num);
    }
    public boolean buyGoods(int gid,int num){
        return US.buyGoods(this,gid,num);
    }
    public void showRecord(){
        US.showRecord(this);
    }

    @Override
    public String toString() {
        return "用户:"+this.uname+",性别:"+this.sex+",余额:"+this.balance;
    }
}
