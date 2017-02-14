package com.skylinetan.energycloud.presenter.impl;

import android.util.Log;

import com.skylinetan.energycloud.bean.Order;
import com.skylinetan.energycloud.presenter.BasePresenter;
import com.skylinetan.energycloud.presenter.IGrabPresenter;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.view.IGrabView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by apple on 2017/2/14.
 */

public class GrabPresenter extends BasePresenter<IGrabView> implements IGrabPresenter {


    private Subscription mSubscription;

    @Override
    public void getGrabOrderList(int page, int limit) {
        RequestManager.getInstance().getRepairSearch(new Subscriber<List<Order>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Order> grabOrders) {
                getView().setData(grabOrders);
            }
        }, page, limit);
    }

    public void startInterval() {
        stopInterval();
        mSubscription = Observable
                .interval(5, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long l) {
                        getGrabOrderList(getView().getPage(),getView().getLimite());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("ERROR",throwable.getMessage());
                    }
                });
    }

    public void stopInterval() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
