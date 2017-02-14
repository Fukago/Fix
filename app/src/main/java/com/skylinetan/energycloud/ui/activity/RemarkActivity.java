package com.skylinetan.energycloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.IRemarkPresenter;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.OrderPresenter;
import com.skylinetan.energycloud.view.IRemarkView;

public class RemarkActivity  extends SilBaseActivity<IRemarkPresenter> implements IRemarkView {



    private Toolbar mToolbar;
    private EditText mEdContent;
    private Button mSend;
    private AppCompatRatingBar appCompatRatingBar;

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        OrderPresenter orderPresenter = new OrderPresenter();
        return new BasePresenterFactory(this, orderPresenter);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_remark;
    }

    @Override
    protected void initViewAndEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mEdContent= (EditText) findViewById(R.id.et_content);
        mSend= (Button) findViewById(R.id.btn_send);
        appCompatRatingBar= (AppCompatRatingBar) findViewById(R.id.rtb_remark);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().sendRemark(intent.getIntExtra("repairlist_id",1)
                        ,appCompatRatingBar.getNumStars()
                        ,mEdContent.getText().toString());

            }
        });
    }

    @Override
    public void success() {
        Toast.makeText(this, "评价成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail() {
        Toast.makeText(this, "评价失败", Toast.LENGTH_SHORT).show();
    }
}
