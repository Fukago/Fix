package com.skylinetan.energycloud.presenter;

import com.skylinetan.energycloud.view.IGrabView;

/**
 * Created by apple on 2017/2/14.
 */

public interface IGrabPresenter extends IPresenter<IGrabView>{
    void getGrabOrderList(int page,int limit);

    void startInterval();

    void stopInterval();
}
