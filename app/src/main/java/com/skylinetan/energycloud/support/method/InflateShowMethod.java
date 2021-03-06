package com.skylinetan.energycloud.support.method;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.skylinetan.energycloud.bean.InfoBean;
import com.skylinetan.energycloud.ui.widget.RenderView;


/**
 * Created by Mr_immortalZ on 2016/10/24.
 * email : mr_immortalz@qq.com
 */

public abstract class InflateShowMethod extends ShowMethod {

    public View inflateView;

    public InflateShowMethod(Activity activity, int layoutId) {
        this.inflateView = LayoutInflater.from(activity).inflate(layoutId, null);
    }

    //注
    public void translate(InfoBean bean, RenderView parent, View child) {
        set.playTogether(
                ObjectAnimator.ofFloat(child, "translationX", 0, -bean.translationX),
                ObjectAnimator.ofFloat(child, "translationY", 0, -bean.translationY),
                ObjectAnimator.ofFloat(child, "scaleX", 1,1/bean.scalling),
                ObjectAnimator.ofFloat(child, "scaleY", 1,1/bean.scalling)
        );
        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(duration).start();
    }

}
