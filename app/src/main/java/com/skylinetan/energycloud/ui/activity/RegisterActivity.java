package com.skylinetan.energycloud.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.HttpWrapper;
import com.skylinetan.energycloud.bean.InfoBean;
import com.skylinetan.energycloud.support.method.ColorShowMethod;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.utils.TransitionsHeleper;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import rx.Subscriber;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_phone)
    EditText registerPhone;
    @BindView(R.id.register_code)
    EditText registerCode;
    @BindView(R.id.nick_name)
    EditText nickName;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_confirm)
    EditText registerConfirm;
    @BindView(R.id.register_button)
    Button registerButton;
    @BindView(R.id.activity_register)
    LinearLayout activityRegister;
    @BindView(R.id.register_count_down)
    CountdownView registerCountDown;
    @BindView(R.id.register_code_tv)
    TextView registerCodeTv;

    private final long TIME = (long)60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(R.color.colorPrimary, R.color.colorPrimary) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(copyView, "rotation", 0, 180),
                                ObjectAnimator.ofFloat(copyView, "scaleX", 1, 0),
                                ObjectAnimator.ofFloat(copyView, "scaleY", 1, 0)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(duration / 4 * 5).start();
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {

                    }
                })
                .show(this, null);

        registerCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCodeTv.setVisibility(View.GONE);
                registerCountDown.setVisibility(View.VISIBLE);
                registerCountDown.start(TIME);

                RequestManager.getInstance().getCode(new Subscriber<HttpWrapper<Object>>() {

                    @Override
                    public void onCompleted() {
                        Toast.makeText(RegisterActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpWrapper<Object> objectHttpWrapper) {

                    }
                }, registerPhone.getText().toString());
            }
        });

        registerCountDown.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                registerCountDown.setVisibility(View.GONE);
                registerCodeTv.setVisibility(View.VISIBLE);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!registerPassword.getText().toString().equals(registerConfirm.getText().toString()))
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                RequestManager.getInstance().verifyCode(new Subscriber<HttpWrapper<Object>>() {

                    @Override
                    public void onCompleted() {
                        Toast.makeText(RegisterActivity.this, "验证信息成功", Toast.LENGTH_SHORT).show();
                        RequestManager.getInstance().register(new Subscriber<HttpWrapper<Object>>() {
                            @Override
                            public void onCompleted() {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                RegisterActivity.this.finish();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(HttpWrapper<Object> objectHttpWrapper) {

                            }
                        }, registerPhone.getText().toString(), nickName.getText().toString(), registerPassword.getText().toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpWrapper<Object> objectHttpWrapper) {

                    }
                }, registerPhone.getText().toString(), registerCode.getText().toString());
            }
        });
    }
}
