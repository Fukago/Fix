package com.skylinetan.energycloud.ui.fragment;

import android.view.View;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.MainFrPresenter;
import com.skylinetan.energycloud.support.Constants;

/**
 * Created by skylineTan on 16/12/3.
 */
public class MainFragment extends SilBaseFragment<MainFrPresenter>{

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        MainFrPresenter mainFrPresenter = new MainFrPresenter();
        return new BasePresenterFactory(this,mainFrPresenter);
    }

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_MAIN_FR;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().test1();
    }

    @Override
    protected void initViewAndEvent(View view) {

    }
}
