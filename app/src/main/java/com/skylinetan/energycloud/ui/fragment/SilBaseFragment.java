package com.skylinetan.energycloud.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.presenter.PresenterLoader;
import com.skylinetan.energycloud.presenter.IPresenter;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.view.IView;

import butterknife.ButterKnife;

/**
 * Created by skylineTan on 16/12/1.
 */
public abstract class SilBaseFragment<P extends IPresenter> extends Fragment implements IView, LoaderManager.LoaderCallbacks<P> {

    private P mPresenter;

    //选择性实现的方法，如果使用mvp，则要创建Loader
    protected PresenterFactory createPresenterFactory(){
        return null;
    }

    //如果使用mvp，则要重写此方法
    protected int getLoaderId(){
        return 0;
    }

    protected abstract int getLayoutId();

    protected abstract void initViewAndEvent(View view);

    //选择性重写的方法
    protected void parseArguments() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArguments();
        getLoaderManager().initLoader(getLoaderId(), null, this);
        if(getPresenter() != null)
            getPresenter().onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getPresenter() != null)
            getPresenter().onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getPresenter() != null)
            getPresenter().onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewAndEvent(view);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getPresenter() != null)
            getPresenter().onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(getPresenter() != null)
            getPresenter().onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(getPresenter() != null)
            getPresenter().onDestory();
    }

    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getActivity(), createPresenterFactory());
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P data) {
        this.mPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        this.mPresenter = null;
    }

    public P getPresenter() {
        return mPresenter;
    }
}
