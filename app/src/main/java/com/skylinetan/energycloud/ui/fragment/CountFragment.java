package com.skylinetan.energycloud.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.Energy;
import com.skylinetan.energycloud.bean.Equipment;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.CountPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.ui.widget.NiceSpinner;
import com.skylinetan.energycloud.view.ICountView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by skylineTan on 16/12/12.
 */
public class CountFragment extends SilBaseFragment<CountPresenter> implements ICountView {

    @BindView(R.id.count_chart)
    BarChart countChart;
    @BindView(R.id.count_pie_chart)
    PieChart countPieChart;
    @BindView(R.id.count_spinner)
    NiceSpinner countSpinner;
    @BindView(R.id.loading_view)
    ProgressBar loadingView;
    @BindView(R.id.count_all_energy)
    TextView countAllEnergy;
    @BindView(R.id.count_average_energy)
    TextView countAverageEnergy;
    @BindView(R.id.count_power_energy)
    TextView countPowerEnergy;
    @BindView(R.id.energy_condition_energy)
    TextView countConditionEnergy;
    @BindView(R.id.energy_special_energy)
    TextView countSpecialEnergy;
    @BindView(R.id.count_light_energy)
    TextView countLightEnergy;

    private String[] mTime = new String[]{
            "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"
    };

    protected String[] mParties = new String[]{
            "主楼顶电梯", "4层弱电", "裙楼电梯", "屋顶灯饰", "应急照明", "形象墙", "厨房", "5楼餐厅锅炉空调",
            "27层办公", "15#", "5-9层照明", "4层舞厅KTV", "3层照明茶厅", "其他", "其他", "其他",
            "其他", "其他", "其他", "其他", "其他", "其他", "其他", "其他",
            "其他", "其他"
    };

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_COUNT;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        CountPresenter countPresenter = new CountPresenter();
        return new BasePresenterFactory(this, countPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_count;
    }

    @Override
    protected void initViewAndEvent(View view) {
        List<String> selectList = new LinkedList<>(Arrays.asList("day", "week", "month", "year"));
        countSpinner.attachDataSource(selectList);
        initChart(mTime);
        initPieChart();
        getEnergyData();
    }

    private void initChart(final String[] time) {
        countChart.getDescription().setEnabled(false);
        countChart.setMaxVisibleValueCount(40);
        countChart.setPinchZoom(false);
        countChart.setDrawGridBackground(false);
        countChart.setDrawBarShadow(false);
        countChart.setDrawValueAboveBar(false);
        countChart.setHighlightFullBarEnabled(false);
        addBarChartData();

        YAxis leftAxis = countChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        countChart.getAxisRight().setEnabled(false);

        XAxis xLabels = countChart.getXAxis();
        xLabels.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return time[(int) value % time.length];
            }
        });
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);

        Legend l = countChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
    }

    public void initPieChart() {
        countPieChart.setUsePercentValues(true);
        countPieChart.getDescription().setEnabled(false);
        countPieChart.setExtraOffsets(5, 10, 5, 5);
        countPieChart.setDragDecelerationFrictionCoef(0.95f);
        countPieChart.setCenterText("分项能耗");

        countPieChart.setDrawHoleEnabled(true);
        countPieChart.setHoleColor(Color.WHITE);

        countPieChart.setTransparentCircleColor(Color.WHITE);
        countPieChart.setTransparentCircleAlpha(110);

        countPieChart.setHoleRadius(58f);
        countPieChart.setTransparentCircleRadius(61f);

        countPieChart.setDrawCenterText(true);

        countPieChart.setRotationAngle(0);
        countPieChart.setRotationEnabled(true);
        countPieChart.setHighlightPerTapEnabled(true);

        addPieChartData();

        countChart.animateXY(3000, 3000);
        //countPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = countPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        //countPieChart.setEntryLabelColor(Color.WHITE);
        //countPieChart.setEntryLabelTextSize(12f);
    }

    private void addBarChartData() {

        final ArrayList<BarEntry> entryArrayList = new ArrayList<>();
        /*float mult = 10;

        for (int i = 0; i < 10; i++) {
            float[] data = new float[20];
            for (int j = 0; j < 20; j++) {
                float val1 = (float) (Math.random() * mult) + 10;
                data[j] = val1;
            }

            entryArrayList.add(new BarEntry(i, data));
        }*/
        showLoading();
        RequestManager.getInstance().getEnergyAll(new Subscriber<List<Energy>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Energy> energies) {
                hideLoading();
                List<BarEntry> entryList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    String allEnergy = energies.get(i + 9).getEnergy_all();
                    String[] energyArray = allEnergy.split(",");
                    float[] floatArray = new float[energyArray.length];
                    for (int j = 0; j < energyArray.length; j++) {
                        floatArray[j] = Float.valueOf(energyArray[j]);
                    }
                    //11 1.2f
                    entryList.add(new BarEntry(i, floatArray));
                }

                BarDataSet dataSet;

                if (countChart.getData() != null &&
                        countChart.getData().getDataSetCount() > 0) {
                    dataSet = (BarDataSet) countChart.getData().getDataSetByIndex(0);
                    dataSet.setValues(entryList);
                    countChart.getData().notifyDataChanged();
                    countChart.notifyDataSetChanged();
                } else {
                    dataSet = new BarDataSet(entryList, "单位能耗");
                    dataSet.setColors(getColors());
                    dataSet.setStackLabels(mParties);

                    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                    dataSets.add(dataSet);

                    BarData data = new BarData(dataSets);
                    //data.setValueFormatter(new MyValueFormatter());
                    data.setValueTextColor(Color.WHITE);

                    countChart.setData(data);
                    countChart.animateXY(3000, 3000);
                    if(countPieChart != null)
                        countPieChart.animateXY(3000, 3000);
                }

                countChart.setFitBars(true);
                countChart.invalidate();
            }
        });
    }

    private void addPieChartData() {

        RequestManager.getInstance().equipmentEnergy(new Subscriber<List<Equipment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Equipment> equipments) {
                final ArrayList<PieEntry> entries = new ArrayList<>();
                Log.e("tzy", "获取到list大小" + equipments.size());
                for (int i = 0; i < equipments.size(); i++) {
                    //mParties[i % mParties.length]
                    entries.add(new PieEntry(equipments.get(i).getDvalue()));
                }
                PieDataSet dataSet = new PieDataSet(entries, "分项能耗");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setValueLinePart1OffsetPercentage(80.f);
                dataSet.setValueLinePart1Length(0.2f);
                dataSet.setValueLinePart2Length(0.4f);
                //dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

                ArrayList<Integer> colors = new ArrayList<>();
                for (int c : ColorTemplate.MATERIAL_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.LIBERTY_COLORS)
                    colors.add(c);
                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);
                colors.add(ColorTemplate.getHoloBlue());
                dataSet.setColors(getPieColors());

                PieData data = new PieData(dataSet);
                data.setDrawValues(true);
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(11f);
                data.setValueTextColor(Color.WHITE);
                countPieChart.setData(data);
                countPieChart.highlightValues(null);
                countPieChart.invalidate();
            }
        });
    }

    private int[] getColors() {
        return new int[]{
                rgb("#4bb87e"), rgb("#6a473d"),rgb("#b1434c"),rgb("#f0652f"),rgb("#c7831e"),
                rgb("#f6aba7"),rgb("#fecd81"),rgb("#6f6d84"),rgb("#607d8b"),rgb("#3d79b1"),
                rgb("#219fd1"), rgb("#82062a"),rgb("#bed541"),rgb("#e91e45")
        };
    }

    private int[] getPieColors() {
        return new int[]{
                rgb("#82062a"), rgb("#e91e45"),rgb("#b1434c"),rgb("#f0652f"),rgb("#c7831e"),
                rgb("#6a473d"), rgb("#bed541"),rgb("#f6aba7"),rgb("#fecd81"),rgb("#6f6d84"),rgb("#607d8b"),rgb("#3d79b1"),
                rgb("#219fd1"), rgb("#4bb87e"),rgb("#f6aba7"),rgb("#f6aba7")
        };
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        countChart.setVisibility(View.GONE);
    }

    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
        countChart.setVisibility(View.VISIBLE);
    }

    public void getEnergyData() {
        RequestManager.getInstance().getEnergyAll(new Subscriber<List<Energy>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Energy> energies) {
                //总能耗
                float sumAll = 0.0f;
                for (int i = 0; i < 24; i++) {
                    String allEnergy = energies.get(i).getEnergy_all();
                    String[] energyArray = allEnergy.split(",");
                    float sum = 0.0f;
                    for(int j = 0; j<energyArray.length; j++){
                        sum += Float.valueOf(energyArray[j]);
                    }
                    sumAll += sum;
                }
                countAllEnergy.setText(String.format("%.2f", sumAll));
                //单位平均能耗
                countAverageEnergy.setText(String.format("%.2f", sumAll / 24.0f));
                float powerAll = 0.0f;
                float conditionAll = 0.0f;
                float specialAll = 0.0f;
                float lightAll = 0.0f;
                for (int i = 0; i < 24; i++) {
                    String allEnergy = energies.get(i).getEnergy_all();
                    String[] energyArray = allEnergy.split(",");
                    float sum = 0.0f;
                    for(int j = 0; j<energyArray.length; j++){
                        sum += Float.valueOf(energyArray[j]);
                    }
                    switch (i){
                        //power
                        case 0:case 2: case 3:
                            powerAll += sum;
                            break;
                        case 1:case 6:case 7:case 8:case 9:case 10:case 12:
                            specialAll += sum;
                            break;
                        case 4:case 5:case 11:case 13:
                            lightAll += sum;
                            break;
                    }
                }
                countPowerEnergy.setText(String.format("%.2f", powerAll));
                countConditionEnergy.setText(String.format("%.2f", conditionAll));
                countSpecialEnergy.setText(String.format("%.2f", specialAll));
                countLightEnergy.setText(String.format("%.2f", lightAll));
            }
        });
    }
}
