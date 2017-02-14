package com.skylinetan.energycloud.presenter;

import com.skylinetan.energycloud.view.IRemarkView;

/**
 * Created by apple on 2017/2/13.
 */

public interface IRemarkPresenter extends IPresenter<IRemarkView>{
    void sendRemark(int equipmentId, int rankStar, String remarkContent);
}
