package com.skylinetan.energycloud.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.RecordPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.ui.adapter.FragmentAdapter;
import com.skylinetan.energycloud.view.IRecordView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by skylineTan on 16/12/3.
 */
public class RecordFragment extends SilBaseFragment<RecordPresenter> implements IRecordView {

    @BindView(R.id.record_tab_layout)
    TabLayout recordTabLayout;
    @BindView(R.id.record_view_pager)
    ViewPager recordViewPager;

    private FragmentAdapter mFragmentAdapter;

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        RecordPresenter recordPresenter = new RecordPresenter();
        return new BasePresenterFactory(this, recordPresenter);
    }

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_RECORD;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_record;
    }

    @Override
    protected void initViewAndEvent(View view) {
        List<String> titleList = new ArrayList<>();
        titleList.add("能耗统计");
        titleList.add("能耗分析");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CountFragment());
        fragmentList.add(new AnalysisFragment());
        mFragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(),
                fragmentList, titleList);
        recordViewPager = (ViewPager) view.findViewById(R.id.record_view_pager);
        recordViewPager.setAdapter(mFragmentAdapter);
        recordTabLayout = (TabLayout) view.findViewById(R.id.record_tab_layout);
        recordTabLayout.setupWithViewPager(recordViewPager);
        recordTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
