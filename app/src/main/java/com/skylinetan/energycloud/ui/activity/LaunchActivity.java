package com.skylinetan.energycloud.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.utils.SPUtils;

/**
 * Created by skylineTan on 16/12/13.
 */
public class LaunchActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String phone = (String) SPUtils.get(LaunchActivity.this, Constants.SP.LOGIN, "");
                if(!phone.equals("")){
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                }else {
                    startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 1000);
    }
}