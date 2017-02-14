package com.skylinetan.energycloud.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.InfoBean;
import com.skylinetan.energycloud.bean.Order;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.IGrabPresenter;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.GrabPresenter;
import com.skylinetan.energycloud.support.method.ColorShowMethod;
import com.skylinetan.energycloud.ui.adapter.OrderAdapter;
import com.skylinetan.energycloud.ui.widget.DividerItemDecoration;
import com.skylinetan.energycloud.utils.TransitionsHeleper;
import com.skylinetan.energycloud.view.IGrabView;

import java.util.List;

/**
 * 抢单活动
 **/
public class GrabActivity extends SilBaseActivity<IGrabPresenter> implements IGrabView<List<Order>>,SwipeRefreshLayout.OnRefreshListener , DatePicker.OnDateChangedListener {


    private AlertDialog mDateDialog;
    private ProgressBar loadingView;
    private TextView errorView;
    private RecyclerView orderRecyclerView;
    private SwipeRefreshLayout orderSwipeRefreshLayout;
    private OrderAdapter mOrderAdapter;

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        GrabPresenter grabPresenter = new GrabPresenter();
        return new BasePresenterFactory(this, grabPresenter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_grab;
    }

    @Override
    protected void initViewAndEvent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(R.color.colorPrimary, R.color.colorPrimary) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(copyView, "alpha", 1f, 0f),
                                ObjectAnimator.ofFloat(copyView, "scaleX", 1.5f, 1f),
                                ObjectAnimator.ofFloat(copyView, "scaleY", 1.5f, 1f)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(duration / 4 * 5).start();
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {

                    }

                })
                .show(this, null);

        loadingView = (ProgressBar) findViewById(R.id.loading_view);
        errorView = (TextView) findViewById(R.id.errorView);
        orderRecyclerView = (RecyclerView) findViewById(R.id.grab_order_recycler_view);
        orderSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.grab_order_swipe_refresh_layout);
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
        mOrderAdapter = new OrderAdapter(this, R.layout.item_order, data, true);
        orderRecyclerView.setAdapter(mOrderAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().startInterval();
    }


    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().startInterval();
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


    @Override
    public int getPage() {
        return 1;
    }

    @Override
    public int getLimite() {
        return 5;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                mDateDialog = new AlertDialog.Builder(this)
                        .setView(R.layout.dialog_date_picker)
                        .show();
                break;
            case R.id.action_user:
                startActivity(new Intent(this, UserActivity.class));
                break;
            case R.id.action_order:
                startActivity(new Intent(this, TotalOrderActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        //年、月、日获取之后，刷新信息
        mDateDialog.dismiss();
    }
}
