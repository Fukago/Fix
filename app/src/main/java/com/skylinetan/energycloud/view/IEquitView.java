package com.skylinetan.energycloud.view;

/**
 * Created by apple on 2017/2/9.
 */

public interface IEquitView<E> extends IView {
    //正在加载
    void showLoading(boolean pullToRefresh);

    //隐藏加载
    void hideLoading();

    //加载错误
    void showError(String msg, boolean pullToRefresh);

    //加载成功之后，设置数据
    void setData(E data);

    //调用presenter加载数据
    void loadData(boolean pullToRefresh);
}
