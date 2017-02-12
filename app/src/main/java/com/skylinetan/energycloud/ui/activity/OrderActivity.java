package com.skylinetan.energycloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.IOrderPresenter;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.OrderPresenter;
import com.skylinetan.energycloud.view.IOrderView;

public class OrderActivity extends SilBaseActivity<IOrderPresenter> implements IOrderView, View.OnClickListener {

    private Toolbar mToolbar;
    private MaterialEditText mTitle;
    private EditText mEdContent;
    private Button mSend;
    private int userId;
    private int equipmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (MaterialEditText) findViewById(R.id.me_tittle);
        mEdContent = (EditText) findViewById(R.id.et_content);
        mSend = (Button) findViewById(R.id.btn_send);
        mSend.setOnClickListener(this);
        Intent intent = getIntent();
        mEdContent.setText("名称：" + intent.getStringExtra("equipment_name") + "\n"
                + "地址：" + intent.getStringExtra("equipment_address") + "\n"
                + "详细地址：" + intent.getStringExtra("name"));
        userId = intent.getIntExtra("user_id", 2);
        equipmentId = intent.getIntExtra("equipment_id", 5);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        OrderPresenter orderPresenter = new OrderPresenter();
        return new BasePresenterFactory(this, orderPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initViewAndEvent() {

    }

    @Override
    public void success() {
        Toast.makeText(this, "订单发布成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail() {
        Toast.makeText(this, "订单发布失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                getPresenter().sendOrder(userId,equipmentId,mTitle.getText().toString(),mEdContent.getText().toString());
                break;
        }
    }
}
