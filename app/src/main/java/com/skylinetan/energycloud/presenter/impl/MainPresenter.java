package com.skylinetan.energycloud.presenter.impl;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.skylinetan.energycloud.presenter.BasePresenter;
import com.skylinetan.energycloud.presenter.IMainPresenter;
import com.skylinetan.energycloud.ui.fragment.EquitFragment;
import com.skylinetan.energycloud.ui.fragment.MapFragment;
import com.skylinetan.energycloud.ui.fragment.MonitorFragment;
import com.skylinetan.energycloud.ui.fragment.RecordFragment;
import com.skylinetan.energycloud.view.IMainView;

/**
 * Created by skylineTan on 16/12/3.
 */
public class MainPresenter extends BasePresenter<IMainView> implements IMainPresenter{

    private MapFragment mMapFragment;
    private Fragment mMonitorFragment;
    private Fragment mRecordFragment;
    private Fragment mEquitFragment;

    @Override
    public void showMainFragment(Intent intent, FragmentTransaction fragmentTransaction, @IdRes int resId) {
        if(mMapFragment == null){
            mMapFragment  = new MapFragment();
            fragmentTransaction.add(resId,mMapFragment);
        }else {
            fragmentTransaction.show(mMapFragment);
        }
    }

    @Override
    public void showMonitorFragment(FragmentTransaction fragmentTransaction, @IdRes int resId) {
        if(mMonitorFragment == null){
            mMonitorFragment = new MonitorFragment();
            fragmentTransaction.add(resId, mMonitorFragment);
        }else {
            fragmentTransaction.show(mMonitorFragment);
        }
    }

    @Override
    public void showAnalysisFragemnt(FragmentTransaction fragmentTransaction, @IdRes int resId) {
        if(mRecordFragment == null){
            mRecordFragment = new RecordFragment();
            fragmentTransaction.add(resId, mRecordFragment);
        }else {
            fragmentTransaction.show(mRecordFragment);
        }
    }

    @Override
    public void showEquitFragment(FragmentTransaction fragmentTransaction, @IdRes int resId) {
        if(mEquitFragment == null){
            mEquitFragment  = new EquitFragment();
            fragmentTransaction.add(resId,mEquitFragment);
        }else {
            fragmentTransaction.show(mEquitFragment);
        }
    }

    @Override
    public void hideFragments(FragmentTransaction transaction) {
        if(mMapFragment != null){
            transaction.hide(mMapFragment);
        }
        if(mMonitorFragment != null){
            transaction.hide(mMonitorFragment);
        }
        if(mRecordFragment != null){
            transaction.hide(mRecordFragment);
        }
        if(mEquitFragment != null){
            transaction.hide(mEquitFragment);
        }
    }
}
