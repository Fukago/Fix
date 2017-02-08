package com.skylinetan.energycloud.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.IAirConditionPresenter;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.AirConditionPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.view.IAirConditionView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by skylineTan on 16/12/10.
 */
public class AirConditionFragment extends SilBaseFragment<IAirConditionPresenter> implements IAirConditionView {

    @BindView(R.id.air_condition_container)
    FrameLayout airConditionContainer;
    @BindView(R.id.air_condition_fab)
    FloatingActionButton airConditionFab;

    private boolean isChartInfo = true;
    private ChartInfoFragment mChartInfoFragment;
    private TextInfoFragment mTextInfoFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_AIR_CONDITION;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        AirConditionPresenter airConditionPresenter = new AirConditionPresenter();
        return new BasePresenterFactory(this, airConditionPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_air_condition;
    }

    @Override
    protected void initViewAndEvent(View view) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        showChartInfoFragment(fragmentTransaction);
        fragmentTransaction.commit();
    }

    private void showChartInfoFragment(FragmentTransaction fragmentTransaction) {
        hideFragment(fragmentTransaction);
        if(mChartInfoFragment == null){
            mChartInfoFragment = new ChartInfoFragment();
            fragmentTransaction.add(R.id.air_condition_container,mChartInfoFragment);
        }else {
            fragmentTransaction.show(mChartInfoFragment);
        }
    }

    private void showTextInfoFragment(FragmentTransaction fragmentTransaction) {
        hideFragment(fragmentTransaction);
        if(mTextInfoFragment == null){
            mTextInfoFragment = new TextInfoFragment();
            fragmentTransaction.add(R.id.air_condition_container,mTextInfoFragment);
        }else {
            fragmentTransaction.show(mTextInfoFragment);
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if(mChartInfoFragment != null)
            fragmentTransaction.hide(mChartInfoFragment);
        if(mTextInfoFragment != null)
            fragmentTransaction.hide(mTextInfoFragment);
    }

    @OnClick(R.id.air_condition_fab)
    void clickFab(){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if(isChartInfo) {
            //跳转到chart text
            showTextInfoFragment(fragmentTransaction);
            airConditionFab.setImageResource(R.mipmap.ic_analysis);
        }else {
            showChartInfoFragment(fragmentTransaction);
            airConditionFab.setImageResource(R.mipmap.ic_category);
        }
        fragmentTransaction.commit();
        isChartInfo = !isChartInfo;
    }
}
