package com.skylinetan.energycloud.support.network;

import com.skylinetan.energycloud.bean.AirCondition1;
import com.skylinetan.energycloud.bean.Building;
import com.skylinetan.energycloud.bean.Energy;
import com.skylinetan.energycloud.bean.Equipment;
import com.skylinetan.energycloud.bean.HttpWrapper;
import com.skylinetan.energycloud.bean.User;
import com.skylinetan.energycloud.support.Constants;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface EnergyApiService {

    //获取验证码
    @GET(Constants.API.GET_CODE)
    Observable<HttpWrapper<Object>> getCode(@Query("phone") String phone);

    //验证验证码是否正确
    @GET(Constants.API.VERIFY_CODE)
    Observable<HttpWrapper<Object>> verifyCode(@Query("phone") String phone, @Query("verify") String verifyCode);

    //注册
    @FormUrlEncoded
    @POST(Constants.API.REGISTER)
    Observable<HttpWrapper<Object>> register(@Field("phone") String phone, @Field("nick_name") String nickName, @Field("password") String password);

    //登录
    @FormUrlEncoded
    @POST(Constants.API.LOGIN)
    Observable<HttpWrapper<Object>> login(@Field("phone") String phone, @Field("password") String password);

    //修改个人信息
    @FormUrlEncoded
    @POST(Constants.API.UPDATE)
    Observable<HttpWrapper<Object>> update(@Field("phone") String phone, @Field("nick_name") String nickName);

    //查询个人信息
    @GET(Constants.API.SEARCH)
    Observable<HttpWrapper<User>> search(@Query("phone") String phone);

    //获取建筑物信息
    @GET(Constants.API.BUILDING_SEARCH)
    Observable<HttpWrapper<Building>> buildingSearch();

    //获取最近一小时设备运行状态
    @GET(Constants.API.BUILDING_NOW)
    Observable<HttpWrapper<List<Equipment>>> equipmentEnergy();

    //每个设备所有小时的能耗
    @GET(Constants.API.BUILDING_GOAL)
    Observable<HttpWrapper<List<Energy>>> getEnergyAll();

    @GET(Constants.API.BUILDING_MONITOR)
    Observable<HttpWrapper<List<AirCondition1>>> getAirCondition(@Query("num") String num);
}