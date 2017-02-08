package com.skylinetan.energycloud.support.widget;

import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.skylinetan.energycloud.R;

/**
 * Created by skylineTan on 16/12/12.
 */
public class CardController  {

    /*private final ImageButton mPlayBtn;
    private final ImageButton mUpdateBtn;
    protected boolean firstStage;

    protected final Runnable unlockAction = new Runnable() {
        @Override
        public void run() {

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    unlock();
                }
            }, 500);
        }
    };

    protected final Runnable showAction = new Runnable() {
        @Override
        public void run() {

            new Handler().postDelayed(new Runnable() {
                public void run() {

                    show(unlockAction);
                }
            }, 5000);
        }
    };

    protected CardController(CardView cardView){
        super();

        RelativeLayout toolbar = (RelativeLayout) cardView.findViewById(R.id.chart_toolbar);
        mPlayBtn = (ImageButton) toolbar.findViewById(R.id.play);
        mUpdateBtn = (ImageButton) toolbar.findViewById(R.id.update);

        mPlayBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss(showAction);
            }
        });

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                update();
            }
        });
    }

    public void init() {

        show(unlockAction);
        //dismiss(showAction);
    }


    protected void show(Runnable action) {

        lock();
        firstStage = false;
    }


    protected void update() {

        lock();
        firstStage = !firstStage;
    }


    protected void dismiss(Runnable action) {

        lock();
    }


    private void lock() {

        mPlayBtn.setEnabled(false);
        mUpdateBtn.setEnabled(false);
    }


    private void unlock() {

        mPlayBtn.setEnabled(true);
        mUpdateBtn.setEnabled(true);
    }*/
}
