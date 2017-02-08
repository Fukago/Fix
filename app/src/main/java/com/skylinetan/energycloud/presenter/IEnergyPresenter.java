package com.skylinetan.energycloud.presenter;

import com.skylinetan.energycloud.view.IEnergyView;

/**
 * Created by skylineTan on 16/12/10.
 */
public interface IEnergyPresenter extends IPresenter<IEnergyView>{

    void loadEquipment(final boolean pullToRefresh);
}
