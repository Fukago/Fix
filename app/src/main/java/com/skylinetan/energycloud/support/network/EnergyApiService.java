package com.skylinetan.energycloud.support.network;

import com.skylinetan.energycloud.bean.AirCondition1;
import com.skylinetan.energycloud.bean.Building;
import com.skylinetan.energycloud.bean.Domiantion;
import com.skylinetan.energycloud.bean.Energy;
import com.skylinetan.energycloud.bean.Equipment;
import com.skylinetan.energycloud.bean.HttpWrapper;
import com.skylinetan.energycloud.bean.Order;
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

    //注册维修工
    @FormUrlEncoded
    @POST(Constants.API.REGISTER_REPAIR)
    Observable<HttpWrapper<Object>> registerRepair(@Field("phone") String phone, @Field("nick_name") String nickName, @Field("password") String password);

    //登录
    @FormUrlEncoded
    @POST(Constants.API.LOGIN)
    Observable<HttpWrapper<Object>> login(@Field("phone") String phone, @Field("password") String password);

    //登陆维修工
    @FormUrlEncoded
    @POST(Constants.API.LOGIN_REPAIR)
    Observable<HttpWrapper<Object>> loginRepair(@Field("phone") String phone, @Field("password") String password);

    //修改个人信息
    @FormUrlEncoded
    @POST(Constants.API.UPDATE)
    Observable<HttpWrapper<Object>> update(@Field("phone") String phone, @Field("nick_name") String nickName);

    //修改维修工个人信息
    @FormUrlEncoded
    @POST(Constants.API.UPDATE_REPAIR)
    Observable<HttpWrapper<Object>> updateRepair(@Field("phone") String phone, @Field("nick_name") String nickName);


    //查询个人信息
    @GET(Constants.API.SEARCH)
    Observable<HttpWrapper<User>> search(@Query("phone") String phone);

    //查询维修公个人信息
    @GET(Constants.API.SEARCH_REPAIR)
    Observable<HttpWrapper<User>> searchRepair(@Query("phone") String phone);


    //获取建筑物信息
    @GET(Constants.API.BUILDING_SEARCH)
    Observable<HttpWrapper<Building>> buildingSearch();

    //管理员清单
    @GET(Constants.API.DOMIANTION_LIST)
    Observable<HttpWrapper<List<Domiantion>>> getDomiantion(@Query("user_id") int userId);

    //添加维修清单
    @FormUrlEncoded
    @POST(Constants.API.REPAIR_LIST)
    Observable<HttpWrapper<Object>> sendOrder(@Field("user_id") int userId, @Field("equipment_id") int equipmetId,
                                              @Field("title") String title, @Field("content") String content);

    //获取最近一小时设备运行状态
    @GET(Constants.API.BUILDING_NOW)
    Observable<HttpWrapper<List<Equipment>>> equipmentEnergy();

    //每个设备所有小时的能耗
    @GET(Constants.API.BUILDING_GOAL)
    Observable<HttpWrapper<List<Energy>>> getEnergyAll();

    @GET(Constants.API.BUILDING_MONITOR)
    Observable<HttpWrapper<List<AirCondition1>>> getAirCondition(@Query("num") String num);

    //查询管理员自己发布的清单
    @GET(Constants.API.USER_SEARCH)
    Observable<HttpWrapper<List<Order>>> getUserSearch(@Query("page") int page, @Query("limit") int limit, @Query("user_id") int userId);

    //评价维修工
    @POST(Constants.API.REMARK_CONTENT)
    Observable<HttpWrapper<Object>> sendRemark(@Field("id") int Id, @Field("rank_star") int rankStar,
                                               @Field("remark_content") String remarkContent);

    //维修工查看有没有订单
    @GET(Constants.API.GRAB_ORDER)
    Observable<HttpWrapper<List<Order>>> getRepairSearch(@Query("page") int page, @Query("limit") int limit);


}