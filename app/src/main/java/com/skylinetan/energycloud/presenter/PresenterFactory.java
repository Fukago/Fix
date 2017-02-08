package com.skylinetan.energycloud.presenter;

import com.skylinetan.energycloud.presenter.IPresenter;

/**
 * Created by skylineTan on 16/12/1.
 */
public interface PresenterFactory<P extends IPresenter> {

    P create();

}
