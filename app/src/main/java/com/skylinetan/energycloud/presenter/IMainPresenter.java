package com.skylinetan.energycloud.presenter;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;

import com.skylinetan.energycloud.view.IMainView;

/**
 * Created by skylineTan on 16/12/3.
 */
public interface IMainPresenter extends IPresenter<IMainView>{

    void showMainFragment(Intent intent, FragmentTransaction fragmentTransaction, @IdRes int resId);

    void showMonitorFragment(FragmentTransaction fragmentTransaction, @IdRes int resId);

    void showAnalysisFragemnt(FragmentTransaction fragmentTransaction, @IdRes int resId);

    void showEquitFragment(FragmentTransaction fragmentTransaction,@IdRes int resId);

    void hideFragments(FragmentTransaction fragmentTransaction);
}
