package com.skylinetan.energycloud.support.widget;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.widget.CardView;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.skylinetan.energycloud.R;

/**
 * Created by skylineTan on 16/12/12.
 */
public class LineCardTwo extends CardController{

    /*private final LineChartView mLineChartView;

    private final String[] mLabels =
            {"06-15 11:47:52", "", "", "", "", "", "11:47:52", "", "", "", "", "", "11:47:52", "", "", "", "", "",
                    "11:47:52", "", "", "", "", "", "11:47:52"};

    private final float[][] mValues =
            {{35f, 37f, 47f, 49f, 43f, 46f, 80f, 83f, 65f, 68f, 28f, 55f, 58f, 50f, 53f, 53f, 57f,
                    48f, 50f, 53f, 54f, 25f, 27f, 35f, 37f},
                    {85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f,
                            85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f}};


    public LineCardTwo(CardView cardView) {

        super(cardView);

        mLineChartView = (LineChartView) cardView.findViewById(R.id.in_out_temp_chart);
    }

    @Override
    protected void show(Runnable action) {
        super.show(action);

        LineSet dataset = new LineSet(mLabels, mValues[0]);

        for (int i = 0; i < mLabels.length; i += 5) {
            Point point = (Point) dataset.getEntry(i);
            point.setColor(Color.parseColor("#ffffff"));
            point.setStrokeColor(Color.parseColor("#0290c3"));
            if (i == 1 || i == 3) point.setRadius(Tools.fromDpToPx(6));
        }

        dataset.setColor(Color.parseColor("#004f7f"))
                .setThickness(Tools.fromDpToPx(3))
                .setSmooth(true)
                .beginAt(0)
                .endAt(24);

        mLineChartView.addData(dataset);

        dataset = new LineSet(mLabels, mValues[1]);
        dataset.setColor(Color.parseColor("#2b908f"));
        dataset.setColor(Color.parseColor("#004f7f"))
                .setThickness(Tools.fromDpToPx(3))
                .setSmooth(true)
                .beginAt(0)
                .endAt(24);
        mLineChartView.addData(dataset);


        Paint thresPaint = new Paint();
        thresPaint.setColor(Color.parseColor("#0079ae"));
        thresPaint.setStyle(Paint.Style.STROKE);
        thresPaint.setAntiAlias(true);
        thresPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
        thresPaint.setPathEffect(new DashPathEffect(new float[] {10, 10}, 0));

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#ffffff"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        mLineChartView.setBorderSpacing(Tools.fromDpToPx(0))
                .setGrid(ChartView.GridType.VERTICAL, 1, 10, gridPaint)
                .setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#304a00"))
                .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setXAxis(true)
                .setYAxis(true)
                .setGrid(ChartView.GridType.VERTICAL, 1, 7, gridPaint)
                .setValueThreshold(80f, 80f, thresPaint)
                .setAxisBorderValues(0, 110);

        Animation anim1 = new Animation().setStartPoint(0, .5f).setEndAction(action);
        Animation anim2 = new Animation().setStartPoint(0, .5f).setEndAction(showAction);

        mLineChartView.show(anim1);
        //mLineChartView.dismiss(anim2);
    }

    @Override
    protected void update() {
        super.update();

        if (firstStage) {
            mLineChartView.updateValues(0, mValues[1]);
        } else {
            mLineChartView.updateValues(0, mValues[0]);
        }
        mLineChartView.notifyDataUpdate();
    }

    @Override
    protected void dismiss(Runnable action) {
        super.dismiss(action);

        mLineChartView.dismiss(new Animation().setStartPoint(1, .5f).setEndAction(action));
    }*/

}
