package com.skylinetan.energycloud.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.InfoBean;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.IMainPresenter;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.MainPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.support.method.ColorShowMethod;
import com.skylinetan.energycloud.utils.TransitionsHeleper;
import com.skylinetan.energycloud.view.IMainView;

public class MainActivity extends SilBaseActivity<IMainPresenter> implements IMainView, BottomNavigationView.OnNavigationItemSelectedListener, DatePicker.OnDateChangedListener {

    //View部分


    private FragmentManager mFragmentManager;
    private BottomNavigationView bottomNavigationView;
    private AlertDialog mDateDialog;
    private int Flag = 0;//默认第一页

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        MainPresenter mainPresenter = new MainPresenter();
        return new BasePresenterFactory(this, mainPresenter);
    }

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_MAIN;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewAndEvent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);
        setSupportActionBar(toolbar);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        getPresenter().hideFragments(fragmentTransaction);
        switch (Flag) {
            case 0:
                getPresenter().showMainFragment(getIntent(), fragmentTransaction, R.id.main_container);
                break;
            case 1:
                getPresenter().showEquitFragment(fragmentTransaction, R.id.main_container);
                break;
            case 2:
                getPresenter().showMonitorFragment(fragmentTransaction, R.id.main_container);
                break;
            case 3:
                getPresenter().showAnalysisFragemnt(fragmentTransaction, R.id.main_container);
                break;
        }
        fragmentTransaction.commit();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        getPresenter().hideFragments(fragmentTransaction);
        switch (item.getItemId()) {
            case R.id.action_home:
                getPresenter().showMainFragment(getIntent(), fragmentTransaction, R.id.main_container);
                Flag = 0;
                break;
            case R.id.action_equit:
                getPresenter().showEquitFragment(fragmentTransaction, R.id.main_container);
                Flag = 1;
                break;
            case R.id.action_monitor:
                getPresenter().showMonitorFragment(fragmentTransaction, R.id.main_container);
                Flag = 2;
                break;
            case R.id.action_analysis:
                getPresenter().showAnalysisFragemnt(fragmentTransaction, R.id.main_container);
                Flag = 3;
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
        return true;
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        //年、月、日获取之后，刷新信息
        mDateDialog.dismiss();
    }
}
