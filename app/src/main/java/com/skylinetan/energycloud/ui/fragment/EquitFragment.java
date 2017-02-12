package com.skylinetan.energycloud.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.Domiantion;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.EquitPresent;
import com.skylinetan.energycloud.ui.adapter.DomiantionAdapter;
import com.skylinetan.energycloud.ui.widget.DividerItemDecoration;
import com.skylinetan.energycloud.view.IEquitView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquitFragment extends SilBaseFragment<EquitPresent> implements IEquitView<List<Domiantion>>, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.loading_view)
    ProgressBar loadingView;
    @BindView(R.id.errorView)
    TextView errorView;
    @BindView(R.id.equit_recycler_view)
    RecyclerView equitRecyclerView;
    @BindView(R.id.equip_swipe_refresh_layout)
    SwipeRefreshLayout equipSwipeRefreshLayout;
    private DomiantionAdapter mDomiantionAdapter;

    public EquitFragment() {
    }

    @Override
    protected int getLoaderId() {
        return 12;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(false);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        EquitPresent equitPresent = new EquitPresent();
        return new BasePresenterFactory(this, equitPresent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_equit;
    }

    @Override
    protected void initViewAndEvent(View view) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {
        loadData(false);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg, boolean pullToRefresh) {
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        equitRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<Domiantion> data) {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        mDomiantionAdapter = new DomiantionAdapter(getContext(), R.layout.item_domiantion, data);
        equitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        equitRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        equitRecyclerView.setAdapter(mDomiantionAdapter);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadEquipment(pullToRefresh);
    }
}

