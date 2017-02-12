package com.skylinetan.energycloud.presenter;

import com.skylinetan.energycloud.view.ITotalOrderVIew;

/**
 * Created by apple on 2017/2/12.
 */

public interface ITotalOrderPresenter extends IPresenter<ITotalOrderVIew> {
    void showOrder(int page,int limit,int user_id);

    void startInterval();

    void stopInterval();
}
