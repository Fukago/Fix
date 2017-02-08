package com.skylinetan.energycloud.bean;

/**
 * Created by skylineTan on 16/12/13.
 */
public class User {

    private String phone;
    private int user_type;
    private String nick_name;
    private String avatar_src;

    public User(){

    }

    public User(String phone, int user_type, String nick_name, String avatar_src) {
        this.phone = phone;
        this.user_type = user_type;
        this.nick_name = nick_name;
        this.avatar_src = avatar_src;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getAvatar_src() {
        return avatar_src;
    }

    public void setAvatar_src(String avatar_src) {
        this.avatar_src = avatar_src;
    }

}
