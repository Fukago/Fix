package com.skylinetan.energycloud.presenter;

import com.skylinetan.energycloud.view.IOrderView;

/**
 * Created by apple on 2017/2/10.
 */

public interface IOrderPresenter extends IPresenter<IOrderView> {
    void showEquipment(int equipmentId);

    void sendOrder(int userId, int equipmentId, String title, String content);

}
