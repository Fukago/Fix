package com.skylinetan.energycloud.presenter.impl;

import android.util.Log;

import com.skylinetan.energycloud.presenter.BasePresenter;
import com.skylinetan.energycloud.presenter.IMainFrPresenter;
import com.skylinetan.energycloud.view.IMainFrView;

/**
 * Created by skylineTan on 16/12/3.
 */
public class MainFrPresenter extends BasePresenter<IMainFrView> implements IMainFrPresenter{

    public void test1(){
        Log.e("skylinetan","1122");
    }

}
