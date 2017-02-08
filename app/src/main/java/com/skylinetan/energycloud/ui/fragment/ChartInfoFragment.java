package com.skylinetan.energycloud.ui.fragment;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.AirCondition1;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.IChartInfoPresenter;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.AirConditionPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.view.IChartInfoView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * Created by skylineTan on 16/12/10.
 */
public class ChartInfoFragment extends SilBaseFragment<IChartInfoPresenter> implements IChartInfoView {

    @BindView(R.id.in_out_temp_chart)
    LineChart inOutTempChart;
    @BindView(R.id.water_flow_chart)
    LineChart waterFlowChart;
    @BindView(R.id.host_power_chart)
    LineChart hostPowerChart;
    @BindView(R.id.cooling_capacity_chart)
    LineChart coolingCapacityChart;
    @BindView(R.id.cop_chart)
    LineChart copChart;

    protected String[] mTempTime = new String[]{
            "11:47:52", "", "", "", "", "", "11:47:52", "", "", "", "", "", "11:47:52", "", "", "", "", "",
            "11:47:52", "", "", "", "", "", "11:47:52"
    };

    private String[] mWaterflowTime = new String[]{
            "11:47:52", "11:47:53", "11:47:54"
    };

    private String[] mHostPowerTime = new String[]{
            "11:47:52", "11:47:52", "11:47:52"
    };

    private String[] mCoolingCapacityTime = new String[]{
            "11:47:52", "11:47:52", "11:47:52"
    };

    private String[] mCopTime = new String[]{
            "11:47:52", "11:47:52", "11:47:52"
    };

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_CHART_INFO;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        AirConditionPresenter airConditionPresenter = new AirConditionPresenter();
        return new BasePresenterFactory(this, airConditionPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_info;
    }

    @Override
    protected void initViewAndEvent(View view) {
        //new LineCardTwo((CardView)view.findViewById(R.id.air_in_out_temp)).init();
        addInOutTempChartData();
        addChartData();
    }

    private void initInOutTempChart() {
        inOutTempChart.getDescription().setEnabled(false);
        inOutTempChart.setTouchEnabled(true);
        inOutTempChart.setDragDecelerationFrictionCoef(0.9f);
        inOutTempChart.setDragEnabled(true);
        inOutTempChart.setScaleEnabled(true);
        inOutTempChart.setDrawGridBackground(false);
        inOutTempChart.setHighlightPerDragEnabled(true);
        inOutTempChart.setPinchZoom(true);
        inOutTempChart.setBackgroundColor(Color.WHITE);

        Legend l = inOutTempChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        XAxis xAxis = inOutTempChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                //Date date = new Date(System.currentTimeMillis());
                //String str = sdf.format(date);
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                String time1 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second - 10);
                String time2 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second - 5);
                String time3 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second);
                String time4 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second);
                String time5 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second);
                mTempTime[0] = time1;
                mTempTime[12] = time2;
                mTempTime[24] = time3;
                return mTempTime[(int) value % mTempTime.length];
            }
        });

        YAxis leftAxis = inOutTempChart.getAxisLeft();
        leftAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        leftAxis.setAxisMaximum(30f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setGranularityEnabled(false);

        YAxis rightAxis = inOutTempChart.getAxisRight();
        rightAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        rightAxis.setAxisMaximum(30f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawGridLines(true);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
    }

    private void addInOutTempChartData() {
       RequestManager.getInstance().getAircondition(new Subscriber<List<AirCondition1>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<AirCondition1> airCondition1s) {
                initInOutTempChart();
                ArrayList<ILineDataSet> dataSetArrayList = new ArrayList<>();
                ArrayList<Entry> inTempEntryList = new ArrayList<>();
                ArrayList<Entry> outTempEntryList = new ArrayList<>();
                for (int i = 0; i < 25; i++) {
                    inTempEntryList.add(new Entry(i, airCondition1s.get(i).getIn_water()));
                }
                for (int i = 0; i < 25; i++) {
                    outTempEntryList.add(new Entry(i, airCondition1s.get(i).getOut_water() / 5));
                }
                LineDataSet inTempDataSet = new LineDataSet(inTempEntryList, "主机入水");
                inTempDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                inTempDataSet.setColor(ContextCompat.getColor(getContext(), R.color.green_light));
                inTempDataSet.setLineWidth(2f);
                inTempDataSet.setDrawCircles(false);
                inTempDataSet.setDrawCircleHole(false);
                inTempDataSet.setDrawValues(false);
                inTempDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                inTempDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                dataSetArrayList.add(inTempDataSet);

                LineDataSet outTempDataSet = new LineDataSet(outTempEntryList, "主机出水");
                outTempDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                outTempDataSet.setColor(ContextCompat.getColor(getContext(), R.color.pink));
                outTempDataSet.setLineWidth(2f);
                outTempDataSet.setDrawCircles(false);
                outTempDataSet.setDrawCircleHole(false);
                outTempDataSet.setDrawValues(false);
                outTempDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                outTempDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                dataSetArrayList.add(outTempDataSet);

                LineData lineData = new LineData(dataSetArrayList);
                inOutTempChart.setData(lineData);
                inOutTempChart.animateXY(3000, 3000);
            }
        }, "25");
    }

    private void initChart(LineChart lineChart, final String[] time, float maximum, float minimum, String type) {
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragDecelerationFrictionCoef(0.9f);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.animateX(2500);

        Legend l = lineChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return time[(int) value % time.length];
            }
        });

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        leftAxis.setAxisMaximum(maximum);
        leftAxis.setAxisMinimum(minimum);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setGranularityEnabled(false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        rightAxis.setAxisMaximum(maximum);
        rightAxis.setAxisMinimum(minimum);
        rightAxis.setDrawGridLines(true);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
    }

    private void addChartData() {
        RequestManager.getInstance().getAircondition(new Subscriber<List<AirCondition1>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<AirCondition1> airCondition1s) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                String time1 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second - 4);
                String time2 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second - 2);
                String time3 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second);
                String time4 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second);
                String time5 = String.valueOf(hour) + ":" +
                        String.valueOf(minute) + ":" + String.valueOf(second);
                mWaterflowTime[0] = time1;
                mWaterflowTime[1] = time2;
                mWaterflowTime[2] = time3;
                mHostPowerTime[0] = time1;
                mHostPowerTime[1] = time2;
                mHostPowerTime[2] = time3;
                mCoolingCapacityTime[0] = time1;
                mCoolingCapacityTime[1] = time2;
                mCoolingCapacityTime[2] = time3;
                mCopTime[0] = time1;
                mCopTime[1] = time2;
                mCopTime[2] = time3;
                initChart(waterFlowChart, mWaterflowTime, 4f, 0f, "冷冻水流量");
                initChart(hostPowerChart, mHostPowerTime, 5f, 0f, "主机功耗");
                initChart(coolingCapacityChart, mCoolingCapacityTime, 2000f, 0f, "冷量");
                initChart(copChart, mCopTime, 2000f, 0f, "COP");
                ArrayList<Entry> waterEntryList = new ArrayList<>();
                ArrayList<Entry> hostEntryList = new ArrayList<>();
                ArrayList<Entry> coolEntryList = new ArrayList<>();
                ArrayList<Entry> copEntryList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    float value = airCondition1s.get(0).getWaterflow();
                    waterEntryList.add(new Entry(i, value));
                }
                for (int i = 0; i < 3; i++) {
                    float value = airCondition1s.get(0).getHost_power();
                    hostEntryList.add(new Entry(i, value));
                }
                for (int i = 0; i < 3; i++) {
                    float value = airCondition1s.get(0).getCooling_capacity();
                    coolEntryList.add(new Entry(i, value));
                }
                for (int i = 0; i < 3; i++) {
                    float value = airCondition1s.get(0).getCop();
                    copEntryList.add(new Entry(i, value));
                }
                LineDataSet waterDataSet = new LineDataSet(waterEntryList, "冷冻水流量");
                LineDataSet hostDataSet = new LineDataSet(hostEntryList, "主机功耗");
                LineDataSet coolDataSet = new LineDataSet(coolEntryList, "冷量");
                LineDataSet copDataSet = new LineDataSet(copEntryList, "COP");
                waterDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                hostDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                coolDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                copDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                waterDataSet.setColor(ContextCompat.getColor(getContext(), R.color.blue_grey));
                waterDataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.blue_grey));
                waterDataSet.setLineWidth(2f);
                waterDataSet.setDrawCircleHole(false);
                waterDataSet.setDrawValues(false);
                waterDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                waterDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

                LineData waterLineData = new LineData(waterDataSet);
                waterFlowChart.setData(waterLineData);
                hostDataSet.setColor(ContextCompat.getColor(getContext(), R.color.purple));
                hostDataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.purple));
                hostDataSet.setLineWidth(2f);
                hostDataSet.setDrawCircleHole(false);
                hostDataSet.setDrawValues(false);
                hostDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                hostDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

                LineData hostLineData = new LineData(hostDataSet);
                hostPowerChart.setData(hostLineData);

                coolDataSet.setColor(ContextCompat.getColor(getContext(), R.color.green_light));
                coolDataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.green_light));
                coolDataSet.setLineWidth(2f);
                coolDataSet.setDrawCircleHole(false);
                coolDataSet.setDrawValues(false);
                coolDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                coolDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

                LineData coolLineData = new LineData(coolDataSet);
                coolingCapacityChart.setData(coolLineData);

                copDataSet.setColor(ContextCompat.getColor(getContext(), R.color.pink));
                copDataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.pink));
                copDataSet.setLineWidth(2f);
                copDataSet.setDrawCircleHole(false);
                copDataSet.setDrawValues(false);
                copDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                copDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

                LineData lineData = new LineData(copDataSet);
                copChart.setData(lineData);
            }
        }, "1");
    }
}
