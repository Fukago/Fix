package com.skylinetan.energycloud.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.ManitorPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.ui.adapter.FragmentAdapter;
import com.skylinetan.energycloud.view.IMonitorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by skylineTan on 16/12/3.
 */
public class MonitorFragment extends SilBaseFragment<ManitorPresenter> implements IMonitorView {

    @BindView(R.id.monitor_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.monitor_view_pager)
    ViewPager mViewPager;

    private FragmentAdapter mFragmentAdapter;

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        ManitorPresenter manitorPresenter = new ManitorPresenter();
        return new BasePresenterFactory(this, manitorPresenter);
    }

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_MONITOR;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_monitor;
    }

    @Override
    protected void initViewAndEvent(View view) {
        List<String> titleList = new ArrayList<>();
        titleList.add("能源监测");
        titleList.add("中央空调监测");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new EnergyFragment());
        fragmentList.add(new AirConditionFragment());
        mFragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(),
                fragmentList, titleList);
        mViewPager = (ViewPager) view.findViewById(R.id.monitor_view_pager);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout = (TabLayout) view.findViewById(R.id.monitor_tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

}
