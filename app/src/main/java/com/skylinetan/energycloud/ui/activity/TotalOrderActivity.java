package com.skylinetan.energycloud.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.Order;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.ITotalOrderPresenter;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.TotalOrderPresenter;
import com.skylinetan.energycloud.ui.adapter.OrderAdapter;
import com.skylinetan.energycloud.ui.widget.DividerItemDecoration;
import com.skylinetan.energycloud.view.ITotalOrderVIew;

import java.util.List;

public class TotalOrderActivity extends SilBaseActivity<ITotalOrderPresenter> implements ITotalOrderVIew<List<Order>> ,SwipeRefreshLayout.OnRefreshListener {



    private ProgressBar loadingView;
    private TextView errorView;
    private RecyclerView orderRecyclerView;
    private SwipeRefreshLayout orderSwipeRefreshLayout;
    private Toolbar mToolbar;
    private OrderAdapter mOrderAdapter;

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        TotalOrderPresenter totalOrderPresenter = new TotalOrderPresenter();
        return new BasePresenterFactory(this, totalOrderPresenter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().startInterval();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_total_order;
    }

    @Override
    protected void initViewAndEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingView= (ProgressBar) findViewById(R.id.loading_view);
        errorView= (TextView) findViewById(R.id.errorView);
        orderRecyclerView= (RecyclerView) findViewById(R.id.order_recycler_view);
        orderSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.order_swipe_refresh_layout);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
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
        orderRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<Order> data) {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        mOrderAdapter = new OrderAdapter(this, R.layout.item_order, data,false);
        orderRecyclerView.setAdapter(mOrderAdapter);
    }


    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().startInterval();
    }

    @Override
    public int getUserId() {
        return 2;
    }

    @Override
    public int getPage() {
        return 1;
    }

    @Override
    public int getLimite() {
        return 5;
    }

    @Override
    public void onRefresh() {
        getPresenter().startInterval();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().stopInterval();
    }


}
