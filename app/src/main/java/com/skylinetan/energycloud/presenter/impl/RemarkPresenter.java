package com.skylinetan.energycloud.presenter.impl;

import com.skylinetan.energycloud.bean.HttpWrapper;
import com.skylinetan.energycloud.presenter.BasePresenter;
import com.skylinetan.energycloud.presenter.IRemarkPresenter;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.view.IRemarkView;

import rx.Subscriber;

/**
 * Created by apple on 2017/2/13.
 */

public class RemarkPresenter extends BasePresenter<IRemarkView> implements IRemarkPresenter {
    @Override
    public void sendRemark(int equipmentId, int rankStar, String remarkContent) {
        RequestManager.getInstance().sendRemark(new Subscriber<HttpWrapper<Object>>() {
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
        }, equipmentId, rankStar, remarkContent);
    }
}
