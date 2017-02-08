package com.skylinetan.energycloud.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by skylineTan on 16/12/3.
 */
public class FragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitleList.size() > 0){
            return mTitleList.get(position);
        }else {
            return super.getPageTitle(position);
        }
    }
}
