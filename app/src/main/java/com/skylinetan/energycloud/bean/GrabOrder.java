package com.skylinetan.energycloud.bean;

/**
 * Created by apple on 2017/2/14.
 * <p>
 * <p>
 * "repairlist_id": 5,
 * "equipment_id": 5,
 * "created_at": "2017-02-12 00:00:00",
 * "equipment_name": "屋顶灯饰",
 * "equipment_type": "照明",
 * "phone": "18883990797"
 */

public class GrabOrder {

    private int repairlist_id;
    private int equipment_id;
    private String created_at;
    private String equipment_name;
    private String equipment_type;
    private String phone;


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRepairlist_id() {
        return repairlist_id;
    }

    public void setRepairlist_id(int repairlist_id) {
        this.repairlist_id = repairlist_id;
    }
}
