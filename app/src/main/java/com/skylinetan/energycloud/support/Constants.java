package com.skylinetan.energycloud.support;

/**
 * Created by skylineTan on 16/12/3.
 */
public class Constants {

    //Load ID
    public static final int LOAD_ID_LOGIN = 1;
    public static final int LOAD_ID_MAIN = 2;
    public static final int LOAD_ID_MAIN_FR = 3;
    public static final int LOAD_ID_MONITOR = 4;
    public static final int LOAD_ID_RECORD = 5;
    public static final int LOAD_ID_ENERGY = 6;
    public static final int LOAD_ID_AIR_CONDITION = 7;
    public static final int LOAD_ID_CHART_INFO = 8;
    public static final int LOAD_ID_TEXT_INFO = 9;
    public static final int LOAD_ID_COUNT = 10;
    public static final int LOAD_ID_ANALYSIS = 11;

    //BUNDLE STRING
    public static final String ARGUMENTS_MAP_FRAGMENT = "arguments_map_fragment";

    public static final class SP{
        public static final String LOGIN = "login";
    }

    public static final class API{
        public static final String BASE_URL = "http://api.nofloat.cn";
        //获取验证码
        public static final String GET_CODE = "/verify";
        //验证验证码是否正确
        public static final String VERIFY_CODE = "/checkVerify";
        //注册
        public static final String REGISTER = "/register";
        //登录
        public static final String LOGIN = "/login";
        //修改个人信息
        public static final String UPDATE = "/update";
        //查询个人信息
        public static final String SEARCH = "/search";
        //查询
        public static final String BUILDING_SEARCH = "/building/search";
        //每个设备的用能状况
        public static final String BUILDING_NOW = "/building/energy_now";
        //每个设备所有小时的能耗
        public static final String BUILDING_GOAL = "/building/goal";
        public static final String BUILDING_MONITOR = "/building/monitor";
    }
}
