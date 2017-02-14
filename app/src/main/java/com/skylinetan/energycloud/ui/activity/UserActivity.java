package com.skylinetan.energycloud.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.HttpWrapper;
import com.skylinetan.energycloud.bean.User;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class UserActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_edit_tv)
    TextView userEditTv;
    @BindView(R.id.user_nick_name)
    TextView userNickName;
    @BindView(R.id.user_phone)
    TextView userPhone;
    private Boolean isAdministrator = null;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        initViewAndEvent();
        isAdministrator = (Boolean) SPUtils.get(UserActivity.this, Constants.SP.ISADMINISTRATER, true);
        if (isAdministrator == null) {
            Toast.makeText(UserActivity.this, "发生未知错误，请尝试清空数据", Toast.LENGTH_SHORT).show();
        } else if (isAdministrator) {
            RequestManager.getInstance().search(new Subscriber<User>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(User user) {
                    mUser = new User();
                    mUser.setNick_name(user.getNick_name());
                    mUser.setPhone(user.getPhone());
                    userNickName.setText(user.getNick_name());
                    userPhone.setText(user.getPhone());
                }
            }, (String) SPUtils.get(this, Constants.SP.LOGIN, ""));
        } else {
            RequestManager.getInstance().searchRepair(new Subscriber<User>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(User user) {
                    mUser = new User();
                    mUser.setNick_name(user.getNick_name());
                    mUser.setPhone(user.getPhone());
                    userNickName.setText(user.getNick_name());
                    userPhone.setText(user.getPhone());
                }
            }, (String) SPUtils.get(this, Constants.SP.LOGIN, ""));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewAndEvent() {
        setSupportActionBar(toolbar);
        //左上角图标是否显示
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //给左上角图标的左边加上一个返回的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.user_edit_tv)
    void clickEditTv() {
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_edit_info, true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
        View view = dialog.getCustomView();
        final TextView editInfoName = (TextView) view.findViewById(R.id.edit_info_name);
        final TextView editInfoNum = (TextView) view.findViewById(R.id.edit_info_num);
        TextView editInfoCancel = (TextView) view.findViewById(R.id.edit_info_cancel);
        TextView editInfoSave = (TextView) view.findViewById(R.id.edit_info_save);
        editInfoName.setText(mUser.getNick_name());
        editInfoNum.setText(mUser.getPhone());
        editInfoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        editInfoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAdministrator == null) {
                    Toast.makeText(UserActivity.this, "发生未知错误，请尝试清空数据", Toast.LENGTH_SHORT).show();
                } else if (isAdministrator) {
                    RequestManager.getInstance().update(new Subscriber<HttpWrapper<Object>>() {
                        @Override
                        public void onCompleted() {
                            dialog.dismiss();
                            userNickName.setText(editInfoName.getText().toString());
                            Toast.makeText(UserActivity.this, "修改信息成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("tzy", "修改信息失败，原因：" + e.getMessage());
                        }

                        @Override
                        public void onNext(HttpWrapper<Object> objectHttpWrapper) {

                        }
                    }, editInfoNum.getText().toString(), editInfoName.getText().toString());
                } else {
                    RequestManager.getInstance().updateRepair(new Subscriber<HttpWrapper<Object>>() {
                        @Override
                        public void onCompleted() {
                            dialog.dismiss();
                            userNickName.setText(editInfoName.getText().toString());
                            Toast.makeText(UserActivity.this, "修改信息成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("tzy", "修改信息失败，原因：" + e.getMessage());
                        }

                        @Override
                        public void onNext(HttpWrapper<Object> objectHttpWrapper) {

                        }
                    }, editInfoNum.getText().toString(), editInfoName.getText().toString());
                }

            }
        });

    }
}
