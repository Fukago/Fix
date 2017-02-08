package com.skylinetan.energycloud.bean;

/**
 * Created by skylineTan on 16/12/10.
 */
public class Equipment {

    /*
    {
      "edId": 42261,
      "eiId": 7,
      "etime": "2016-10-23 19:01:00",
      "dvalue": 0,
      "evalue": 933.6, 某一个时刻的总能耗
      "id": 7,
      "building_id": 1,
      "equipment_name": "形象墙",
      "operating_status": 1,
      "warning_hour": 1000,
      "equipment_type": "特殊"
    },
     */

    private float dvalue;
    private float evalue;
    private int eiId;
    private String equipment_name;
    private int operating_status;
    private int warning_hour;
    private float energy_hour;

    public Equipment(int equipment_id, String equipment_name, int operating_status, int warning_hour, float energy_hour) {
        this.eiId = equipment_id;
        this.equipment_name = equipment_name;
        this.operating_status = operating_status;
        this.warning_hour = warning_hour;
        this.energy_hour = energy_hour;
    }

    public int getEquipment_id() {
        return eiId;
    }

    public void setEquipment_id(int equipment_id) {
        this.eiId = equipment_id;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public int getOperating_status() {
        return operating_status;
    }

    public void setOperating_status(int operating_status) {
        this.operating_status = operating_status;
    }

    public int getWarning_hour() {
        return warning_hour;
    }

    public void setWarning_hour(int warning_hour) {
        this.warning_hour = warning_hour;
    }

    public float getEnergy_hour() {
        return energy_hour;
    }

    public void setEnergy_hour(float energy_hour) {
        this.energy_hour = energy_hour;
    }

    public float getDvalue() {
        return dvalue;
    }

    public void setDvalue(float dvalue) {
        this.dvalue = dvalue;
    }

    public float getEvalue() {
        return evalue;
    }

    public void setEvalue(float evalue) {
        this.evalue = evalue;
    }

    public int getEiId() {
        return eiId;
    }

    public void setEiId(int eiId) {
        this.eiId = eiId;
    }
}
