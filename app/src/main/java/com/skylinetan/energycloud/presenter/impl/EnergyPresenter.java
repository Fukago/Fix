package com.skylinetan.energycloud.presenter.impl;

import android.os.Bundle;
import android.util.Log;

import com.skylinetan.energycloud.bean.Equipment;
import com.skylinetan.energycloud.presenter.BasePresenter;
import com.skylinetan.energycloud.presenter.IEnergyPresenter;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.view.IEnergyView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by skylineTan on 16/12/10.
 */
public class EnergyPresenter extends BasePresenter<IEnergyView> implements IEnergyPresenter{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //model
    }


    @Override
    public void loadEquipment(boolean pullToRefresh) {
        RequestManager.getInstance().equipmentEnergy(new Subscriber<List<Equipment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Equipment> equipments) {
                getView().setData(equipments);
            }
        });
    }
}
