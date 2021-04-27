package cn.jx.xxy;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Register{
    private String account;
    private String password;
    private String status;
    private final static String regex="[a-zA-Z0-9]\\w+@\\w+\\.(cn|com|edu.com|net|qq.com)";
    private final static String regex1="(?=.*\\d)(?=.*[a-zA-Z])\\w{6,16}";
    private final static String regex2="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])\\w{6,16}";
    private final static String regex3="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}";

    public String getMail() {
        return account;
    }

    public void setMail(String mail) throws RegisterException {
        if (judgeAccount(mail)){
            this.account=mail;
        }else throw new RegisterException("邮箱格式错误!!!!");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws RegisterException {
        if (this.judgePd(password)){
            this.password=password;
        }else {
            throw new RegisterException("密码格式错误密码长度6-16位至少包含数字和字母!!!!!");
        }
    }

    public boolean judgeAccount(String mail){
        Pattern pat =Pattern.compile(regex);
        Matcher mat = pat.matcher(mail);
        return mat.matches();

    }
    public boolean judgePd(String password){
        Pattern pat =Pattern.compile(regex1);
        Matcher mat = pat.matcher(password);
        if (mat.matches()){
            this.status="低";
            pat=Pattern.compile(regex2);
            mat = pat.matcher(password);
            if (mat.matches()) {
                this.status="中";
                pat=Pattern.compile(regex3);
                mat = pat.matcher(password);
                if (mat.matches()) {
                    this.status="高";
                }
            }
            return true;
        }else {
            return false;
        }

    }

    @Override
    public String toString() {
        if (this.account==null||this.password==null){
            return "注册失败";
        }else {
            return
                    "注册成功!\taccount:" + account + '\t' +
                            ", password:" + password +'\t'+"密码强度"+this.status;
        }
    }
}
