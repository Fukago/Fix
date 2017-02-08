package com.skylinetan.energycloud.presenter;

import com.skylinetan.energycloud.view.ILoginView;

/**
 * Created by skylineTan on 16/12/3.
 */
public interface ILoginPresenter extends IPresenter<ILoginView>{

    void loginVerify(String phone);
}
