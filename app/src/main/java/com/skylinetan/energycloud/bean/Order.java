package com.skylinetan.energycloud.bean;

/**
 * Created by apple on 2017/2/12.
 * {
 * "repairlist_id": 1,
 * "equipment_id": 14,
 * "created_at": "2017-02-10 00:00:00",
 * "status": "1",
 * "equipment_name": "4层舞厅KTV",
 * "equipment_type": "特殊",
 * "phone": null
 * },
 */

public class Order {
    private int repairlist_id;
    private int equipment_id;
    private String created_at;
    private String status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
