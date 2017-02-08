package com.skylinetan.energycloud.view;

/**
 * Created by skylineTan on 16/12/10.
 */
public interface IEnergyView<M> extends IView{

    //正在加载
    void showLoading(boolean pullToRefresh);

    //隐藏加载
    void hideLoading();

    //加载错误
    void showError(String msg, boolean pullToRefresh);

    //加载成功之后，设置数据
    void setData(M data);

    //调用presenter加载数据
    void loadData(boolean pullToRefresh);
}
