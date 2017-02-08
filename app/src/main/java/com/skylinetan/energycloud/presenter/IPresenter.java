package com.skylinetan.energycloud.presenter;

import android.os.Bundle;

import com.skylinetan.energycloud.view.IView;

/**
 * Created by skylineTan on 16/11/29.
 */
public interface IPresenter<V extends IView>{

    void attachView(V view);

    void onCreate(Bundle savedInstanceState);

    void onDestory();

    void onResume();

    void onPause();

    void onStart();

    void onStop();
}
