package com.skylinetan.energycloud.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.Building;
import com.skylinetan.energycloud.bean.Energy;
import com.skylinetan.energycloud.bean.Equipment;
import com.skylinetan.energycloud.bean.MapInfo;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.EnergyPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.ui.adapter.EquipmentAdapter;
import com.skylinetan.energycloud.ui.adapter.MapInfoAdapter;
import com.skylinetan.energycloud.ui.widget.DividerItemDecoration;
import com.skylinetan.energycloud.view.IEnergyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import rx.Subscriber;

/**
 * Created by skylineTan on 16/12/10.
 */
public class EnergyFragment extends SilBaseFragment<EnergyPresenter> implements IEnergyView<List<Equipment>>, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.loading_view)
    ProgressBar loadingView;
    @BindView(R.id.errorView)
    TextView errorView;
    @BindView(R.id.energy_recycler_view)
    RecyclerView energyRecyclerView;
    @BindView(R.id.main_swipe_refresh_layout)
    SwipeRefreshLayout mainSwipeRefreshLayout;
    @BindView(R.id.energy_chart)
    LineChart energyChart;
    @BindView(R.id.equipment_id)
    TextView equipmentId;
    @BindView(R.id.equipment_name)
    TextView equipmentName;
    @BindView(R.id.operating_state)
    TextView operatingState;
    @BindView(R.id.energy_hour)
    TextView energyHour;
    @BindView(R.id.warning_hour)
    TextView warningHour;
    @BindView(R.id.equipment_edit)
    TextView equipmentEdit;
    @BindView(R.id.energy_today_energy)
    TextView energyTodayEnergy;
    @BindView(R.id.energy_month_energy)
    TextView energyMonthEnergy;
    @BindView(R.id.energy_last_month_energy)
    TextView energyLastMonthEnergy;
    @BindView(R.id.energy_money)
    TextView energyMoney;

    private EquipmentAdapter mEquipmentAdapter;
    private LineChart mLineChart;
    protected String[] mHour = new String[]{
            "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"
    };

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        EnergyPresenter energyPresenter = new EnergyPresenter();
        return new BasePresenterFactory(this, energyPresenter);
    }

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_ENERGY;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_energy;
    }

    @Override
    protected void initViewAndEvent(View view) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLineChart = energyChart;
        addChartData(11, 2.4f);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(false);
    }

    @Optional
    @Override
    public void showLoading(boolean pullToRefresh) {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        energyChart.setVisibility(View.GONE);
    }

    @Optional
    @Override
    public void hideLoading() {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        energyChart.setVisibility(View.VISIBLE);
    }

    @Optional
    @Override
    public void showError(String msg, boolean pullToRefresh) {
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        energyRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<Equipment> equipmentList) {
        //获取数据之后设置数据
        /*equipmentList.add(new Equipment(1, "永辉生活水泵", 2, 1000, 0));
        equipmentList.add(new Equipment(1, "主楼顶电梯", 2, 1000, 8.2f));
        equipmentList.add(new Equipment(1, "", 2, 1000, 9.8f));
        equipmentList.add(new Equipment(1, "", 2, 1000, 2.6f));
        equipmentList.add(new Equipment(1, "", 2, 1000, 0));*/
        mEquipmentAdapter = new EquipmentAdapter(getContext(), R.layout.item_equipment, equipmentList);
        energyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        energyRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        //View view_header = LayoutInflater.from(getActivity()).inflate(R.layout.item_energy_header, null);
        //View view_footer = LayoutInflater.from(getActivity()).inflate(R.layout.item_energy_footer, null);
        //mEquipmentAdapter.addHeaderView(view_header);
        //mEquipmentAdapter.addFooterView(view_footer);
        energyRecyclerView.setAdapter(mEquipmentAdapter);
        initCardView();
    }

    private void initCardView() {
        RequestManager.getInstance().buildingSearch(new Subscriber<Building>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Building building) {
                energyTodayEnergy.setText(building.getD_value() + "");
                energyMonthEnergy.setText(building.getE_value() + "");
                energyLastMonthEnergy.setText("120358.5");
                energyMoney.setText(building.getD_value() + "");
            }
        });
    }

    private void initChart() {
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setTouchEnabled(true);
        mLineChart.setDragDecelerationFrictionCoef(0.9f);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setHighlightPerDragEnabled(true);
        mLineChart.setPinchZoom(true);
        mLineChart.setBackgroundColor(Color.WHITE);


        Legend l = mLineChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mHour[(int) value % mHour.length];
            }
        });

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        leftAxis.setAxisMaximum(12f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setGranularityEnabled(false);

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.grey_dark));
        rightAxis.setAxisMaximum(12f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawGridLines(true);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

        //addChartData(11, 2.4f);
        //mLineChart.animateX(2500);
    }

    private void addChartData(int count, float range) {
        showLoading(false);
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
                ArrayList<Entry> entryList = new ArrayList<>();
                for (int i = 0; i < 11; i++) {
                    String allEnergy = energies.get(i + 9).getEnergy_all();
                    String[] energyArray = allEnergy.split(",");
                    float sum = 0.0f;
                    for(int j = 0; j<energyArray.length; j++){
                        sum += Float.valueOf(energyArray[j]);
                    }
                    //11 1.2f
                    entryList.add(new Entry(i, sum));
                }
                initChart();
                LineDataSet dataSet;
                /*if (mLineChart.getData() != null && mLineChart.getData().getDataSetCount() != 0) {
                    dataSet = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
                    dataSet.setValues(entryList);
                    mLineChart.getData().notifyDataChanged();
                    mLineChart.notifyDataSetChanged();
                } else {*/
                    dataSet = new LineDataSet(entryList, "实时监测走势图");
                    dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                    dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    dataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    dataSet.setLineWidth(2f);
                    dataSet.setCircleRadius(5f);
                    dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    dataSet.setFillAlpha(65);
                    dataSet.setFillColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    dataSet.setHighLightColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    dataSet.setDrawValues(true);
                    dataSet.setValueTextSize(10f);
                    dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                    //dataSet.setDrawCircleHole(true);
                    dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

                    LineData lineData = new LineData(dataSet);
                    mLineChart.setData(lineData);
                    mLineChart.animateX(2500);
                //}
            }
        });
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().loadEquipment(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
