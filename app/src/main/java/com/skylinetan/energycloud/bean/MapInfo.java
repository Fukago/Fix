package com.skylinetan.energycloud.bean;

/**
 * Created by skylineTan on 16/12/13.
 */
public class MapInfo {

    private String mapInfoName;
    private String mapInfoNum;

    public MapInfo(String mapInfoName, String mapInfoNum) {
        this.mapInfoNum = mapInfoNum;
        this.mapInfoName = mapInfoName;
    }

    public String getMapInfoName() {
        return mapInfoName;
    }

    public void setMapInfoName(String mapInfoName) {
        this.mapInfoName = mapInfoName;
    }

    public String getMapInfoNum() {
        return mapInfoNum;
    }

    public void setMapInfoNum(String mapInfoNum) {
        this.mapInfoNum = mapInfoNum;
    }
}
