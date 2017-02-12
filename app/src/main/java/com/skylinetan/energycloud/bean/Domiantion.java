package com.skylinetan.energycloud.bean;

/**
 * Created by apple on 2017/2/9.
 * {
 * "status": 200,
 * "info": "success",
 * "data": {
 * "id": 2,
 * "user_id": 2,
 * "building_id": 1,
 * "equipment_id": 2,
 * "name": "重庆喜百年酒店（南岸店）",
 * "address": "重庆领鑫安地址",
 * "area": 20,
 * "type": 3,
 * "user_num": 500,
 * "equipment_name": "主楼顶电梯",
 * "operating_status": 1,
 * "warning_hour": 100,
 * "equipment_type": "动力"
 * }
 * }
 */

public class Domiantion {
    private int id;
    private int user_id;
    private int building_id;
    private int equipment_id;
    private String name;
    private String address;
    private int area;
    private int type;
    private int user_num;
    private String equipment_name;
    private int operating_status;
    private int warning_hour;
    private String equipment_type;

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

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }

    public int getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(int equipment_id) {
        this.equipment_id = equipment_id;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public String getEquipment_type() {
        return equipment_type;
    }

    public void setEquipment_type(String equipment_type) {
        this.equipment_type = equipment_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOperating_status() {
        return operating_status;
    }

    public void setOperating_status(int operating_status) {
        this.operating_status = operating_status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_num() {
        return user_num;
    }

    public void setUser_num(int user_num) {
        this.user_num = user_num;
    }

    public int getWarning_hour() {
        return warning_hour;
    }

    public void setWarning_hour(int warning_hour) {
        this.warning_hour = warning_hour;
    }
}
