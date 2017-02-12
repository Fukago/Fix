package com.skylinetan.energycloud.presenter;

import com.skylinetan.energycloud.view.IEquitView;

/**
 * Created by apple on 2017/2/9.
 */

public interface IEquitmentPresent extends IPresenter<IEquitView> {
    void loadEquipment(final boolean pullToRefresh);
}
