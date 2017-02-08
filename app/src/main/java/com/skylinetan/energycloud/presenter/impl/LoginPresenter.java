package com.skylinetan.energycloud.presenter.impl;

import android.content.Context;
import android.content.Intent;

import com.skylinetan.energycloud.bean.User;
import com.skylinetan.energycloud.presenter.BasePresenter;
import com.skylinetan.energycloud.presenter.ILoginPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.ui.activity.LoginActivity;
import com.skylinetan.energycloud.ui.activity.MainActivity;
import com.skylinetan.energycloud.utils.SPUtils;
import com.skylinetan.energycloud.view.ILoginView;

/**
 * Created by skylineTan on 16/12/3.
 */
public class LoginPresenter extends BasePresenter<ILoginView> implements ILoginPresenter{

    private Context mContext;

    public LoginPresenter(Context context){
        mContext = context;
        //model
    }

    @Override
    public void loginVerify(String phone) {
        //登录成功
        SPUtils.set(mContext, Constants.SP.LOGIN, phone);
    }
}
