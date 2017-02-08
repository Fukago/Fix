package com.skylinetan.energycloud.bean;

/**
 * Created by skylineTan on 16/12/14.
 */
public class Building {
    /*"data": {
        "id": 1,
                "name": "重庆喜百年酒店（南岸店）",
                "address": "重庆领鑫安地址",
                "area": 20,
                "type": 3,
                "user_num": 500,
                "e_value": 119538.2,
                "d_value": 1022.9,
                "today_e_value": 0,
                "today_d_value": 0
    }*/
    private String name;
    private String address;
    private int area;
    private int type;
    private int user_num;
    private float e_value;
    private float d_value;
    private float today_e_value;
    private float today_d_value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getUser_num() {
        return user_num;
    }

    public void setUser_num(int user_num) {
        this.user_num = user_num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getE_value() {
        return e_value;
    }

    public void setE_value(float e_value) {
        this.e_value = e_value;
    }

    public float getD_value() {
        return d_value;
    }

    public void setD_calue(float d_calue) {
        this.d_value = d_calue;
    }

    public float getToday_e_value() {
        return today_e_value;
    }

    public void setToday_e_value(float today_e_value) {
        this.today_e_value = today_e_value;
    }

    public float getToday_d_value() {
        return today_d_value;
    }

    public void setToday_d_value(float today_d_value) {
        this.today_d_value = today_d_value;
    }
}
