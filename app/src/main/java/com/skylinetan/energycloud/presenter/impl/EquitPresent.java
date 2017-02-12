package com.skylinetan.energycloud.presenter.impl;

import android.os.Bundle;
import android.util.Log;

import com.skylinetan.energycloud.bean.Domiantion;
import com.skylinetan.energycloud.presenter.BasePresenter;
import com.skylinetan.energycloud.presenter.IEquitmentPresent;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.view.IEquitView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by apple on 2017/2/9.
 */

public class EquitPresent extends BasePresenter<IEquitView> implements IEquitmentPresent {
    @Override
    public void loadEquipment(boolean pullToRefresh) {
        RequestManager.getInstance().getDomiantionList(new Subscriber<List<Domiantion>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("error",e.toString());
            }

            @Override
            public void onNext(List<Domiantion> domiantions) {
                getView().setData(domiantions);
            }
        },2);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
