package com.skylinetan.energycloud.bean;

/**
 * Created by skylineTan on 16/12/12.
 */
public class AirCondition {

    private String textInfoName;
    private String textInfoNum;

    public AirCondition(String textInfoName, String textInfoNum) {
        this.textInfoName = textInfoName;
        this.textInfoNum = textInfoNum;
    }

    public String getTextInfoName() {
        return textInfoName;
    }

    public void setTextInfoName(String textInfoName) {
        this.textInfoName = textInfoName;
    }

    public String getTextInfoNum() {
        return textInfoNum;
    }

    public void setTextInfoNum(String textInfoNum) {
        this.textInfoNum = textInfoNum;
    }
}
