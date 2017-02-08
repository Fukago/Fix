package com.skylinetan.energycloud.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.HttpWrapper;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.LoginPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.utils.TransitionsHeleper;
import com.skylinetan.energycloud.view.ILoginView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

public class LoginActivity extends SilBaseActivity<LoginPresenter> implements ILoginView {

    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_to_register)
    TextView loginToRegister;
    @BindView(R.id.login_button)
    Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        LoginPresenter loginPresenter = new LoginPresenter(this);
        return new BasePresenterFactory(this, loginPresenter);
    }

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_LOGIN;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewAndEvent() {
        final TextView textView = (TextView) findViewById(R.id.login_to_register);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionsHeleper.startActivity(LoginActivity.this, RegisterActivity.class, textView);
            }
        });
    }

    @OnClick(R.id.login_button)
    void clickLoginBtn() {
        RequestManager.getInstance().login(new Subscriber<HttpWrapper<Object>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpWrapper<Object> objectHttpWrapper) {
                if (objectHttpWrapper.getStatus() == 200) {
                    getPresenter().loginVerify(loginPhone.getText().toString());
                    TransitionsHeleper.startActivity(LoginActivity.this, MainActivity.class, loginButton);
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, "手机号或密码错误～", Toast.LENGTH_SHORT).show();
                }
            }
        }, loginPhone.getText().toString(), loginPassword.getText().toString());
    }
}
