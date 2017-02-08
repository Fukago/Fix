package com.skylinetan.energycloud.bean;

/**
 * Created by skylineTan on 16/12/14.
 */
public class Energy {

    private int energy_time;
    private String energy_all;
    private String before_energy_all;

    public int getEnergy_time() {
        return energy_time;
    }

    public void setEnergy_time(int energy_time) {
        this.energy_time = energy_time;
    }

    public String getEnergy_all() {
        return energy_all;
    }

    public void setEnergy_all(String energy_all) {
        this.energy_all = energy_all;
    }

    public String getBefore_energy_all() {
        return before_energy_all;
    }

    public void setBefore_energy_all(String before_energy_all) {
        this.before_energy_all = before_energy_all;
    }
}
