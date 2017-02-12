package com.skylinetan.energycloud.presenter.impl;

import com.skylinetan.energycloud.bean.HttpWrapper;
import com.skylinetan.energycloud.presenter.BasePresenter;
import com.skylinetan.energycloud.presenter.IOrderPresenter;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.view.IOrderView;

import rx.Subscriber;

/**
 * Created by apple on 2017/2/10.
 */

public class OrderPresenter extends BasePresenter<IOrderView> implements IOrderPresenter {
    @Override
    public void showEquipment(int equipmentId) {

    }

    @Override
    public void sendOrder(int userId, int equipmentId, String title, String content) {
        RequestManager.getInstance().sendOrder(new Subscriber<HttpWrapper<Object>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().fail();
            }

            @Override
            public void onNext(HttpWrapper<Object> objectHttpWrapper) {
                getView().success();
            }
        }, userId, equipmentId, title, content);
    }
}
