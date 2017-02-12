package com.skylinetan.energycloud.support.network;

import com.skylinetan.energycloud.BuildConfig;
import com.skylinetan.energycloud.bean.AirCondition1;
import com.skylinetan.energycloud.bean.Building;
import com.skylinetan.energycloud.bean.Domiantion;
import com.skylinetan.energycloud.bean.Energy;
import com.skylinetan.energycloud.bean.Equipment;
import com.skylinetan.energycloud.bean.HttpWrapper;
import com.skylinetan.energycloud.bean.User;
import com.skylinetan.energycloud.support.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public enum RequestManager {

    INSTANCE;

    private static final int DEFAULT_TIMEOUT = 30;
    private EnergyApiService energyApiService;
    private OkHttpClient okHttpClient;

    RequestManager() {
        okHttpClient = configureOkHttp(new OkHttpClient.Builder());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(new QualifiedTypeConverterFactory(
                        GsonConverterFactory.create(), SimpleXmlConverterFactory.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        energyApiService = retrofit.create(EnergyApiService.class);
    }

    public static RequestManager getInstance() {
        return INSTANCE;
    }

    public OkHttpClient configureOkHttp(OkHttpClient.Builder builder) {
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    //获取验证码
    public void getCode(Subscriber<HttpWrapper<Object>> subscriber, String phone) {
        Observable<HttpWrapper<Object>> observable = energyApiService.getCode(phone);
        emitObservable(observable, subscriber);
    }

    //验证验证码是否正确
    public void verifyCode(Subscriber<HttpWrapper<Object>> subscriber, String phone, String code) {
        Observable<HttpWrapper<Object>> observable = energyApiService.verifyCode(phone, code);
        emitObservable(observable, subscriber);
    }

    //登录
    public void register(Subscriber<HttpWrapper<Object>> subscriber, String phone, String nickName, String password) {
        Observable<HttpWrapper<Object>> observable = energyApiService.register(phone, nickName, password);
        emitObservable(observable, subscriber);
    }
    //注册管理员
    public void registerRepair(Subscriber<HttpWrapper<Object>> subscriber, String phone, String nickName, String password) {
        Observable<HttpWrapper<Object>> observable = energyApiService.register(phone, nickName, password);
        emitObservable(observable, subscriber);
    }

    //注册
    public void login(Subscriber<HttpWrapper<Object>> subscriber, String phone, String password) {
        Observable<HttpWrapper<Object>> observable = energyApiService.login(phone, password);
        emitObservable(observable, subscriber);
    }


    //上传订单
    public void sendOrder(Subscriber<HttpWrapper<Object>> subscriber, int userId, int equipmentId, String title, String content) {
        Observable<HttpWrapper<Object>> observable = energyApiService.sendOrder(userId, equipmentId, title, content);
        emitObservable(observable, subscriber);
    }

    //修改个人信息
    public void update(Subscriber<HttpWrapper<Object>> subscriber, String phone, String nickName) {
        Observable<HttpWrapper<Object>> observable = energyApiService.update(phone, nickName);
        emitObservable(observable, subscriber);
    }

    //查询个人信息
    public void search(Subscriber<User> subscriber, String phone) {
        Observable<User> observable = energyApiService.search(phone)
                .map(new Func1<HttpWrapper<User>, User>() {
                    @Override
                    public User call(HttpWrapper<User> userHttpWrapper) {
                        return userHttpWrapper.getData();
                    }
                });
        emitObservable(observable, subscriber);
    }

    //获取建筑物信息
    public void buildingSearch(Subscriber<Building> subscriber) {
        Observable<Building> observable = energyApiService.buildingSearch()
                .map(new Func1<HttpWrapper<Building>, Building>() {
                    @Override
                    public Building call(HttpWrapper<Building> buildingHttpWrapper) {
                        return buildingHttpWrapper.getData();
                    }
                });
        emitObservable(observable, subscriber);
    }

    //获取最近一小时设备运行状态
    public void equipmentEnergy(Subscriber<List<Equipment>> subscriber) {
        Observable<List<Equipment>> observable = energyApiService.equipmentEnergy()
                .map(new Func1<HttpWrapper<List<Equipment>>, List<Equipment>>() {
                    @Override
                    public List<Equipment> call(HttpWrapper<List<Equipment>> equipmentHttpWrapper) {
                        return equipmentHttpWrapper.getData();
                    }
                });
        emitObservable(observable, subscriber);
    }

    //每个设备所有小时的能耗
    public void getEnergyAll(Subscriber<List<Energy>> subscriber) {
        Observable<List<Energy>> observable = energyApiService.getEnergyAll()
                .map(new Func1<HttpWrapper<List<Energy>>, List<Energy>>() {
                    @Override
                    public List<Energy> call(HttpWrapper<List<Energy>> listHttpWrapper) {
                        return listHttpWrapper.getData();
                    }
                });
        emitObservable(observable, subscriber);
    }

    public void getAircondition(Subscriber<List<AirCondition1>> subscriber, String num) {
        Observable<List<AirCondition1>> observable = energyApiService.getAirCondition(num)
                .map(new Func1<HttpWrapper<List<AirCondition1>>, List<AirCondition1>>() {
                    @Override
                    public List<AirCondition1> call(HttpWrapper<List<AirCondition1>> listHttpWrapper) {
                        return listHttpWrapper.getData();
                    }
                });
        emitObservable(observable, subscriber);
    }

    public void getDomiantionList(Subscriber<List<Domiantion>> subscriber, int userId) {
        Observable<List<Domiantion>> observable = energyApiService.getDomiantion(userId)
                .map(new Func1<HttpWrapper<List<Domiantion>>, List<Domiantion>>() {
                    @Override
                    public List<Domiantion> call(HttpWrapper<List<Domiantion>> domiantionHttpWrapper) {
                        return domiantionHttpWrapper.getData();
                    }
                });
        emitObservable(observable, subscriber);
    }

    private <T> Subscription emitObservable(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}

