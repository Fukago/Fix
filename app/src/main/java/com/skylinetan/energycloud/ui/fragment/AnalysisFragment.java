package com.skylinetan.energycloud.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.Energy;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.AnalysisPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.ui.widget.NiceSpinner;
import com.skylinetan.energycloud.view.IAnalysisView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by skylineTan on 16/12/12.
 */
public class AnalysisFragment extends SilBaseFragment<AnalysisPresenter> implements IAnalysisView, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    @BindView(R.id.analysis_chart)
    LineChart analysisChart;
    @BindView(R.id.analysis_spinner)
    NiceSpinner analysisSpinner;
    @BindView(R.id.analysis_before_energy)
    TextView analysisBeforeEnergy;
    @BindView(R.id.analysis_after_energy)
    TextView analysisAfterEnergy;
    @BindView(R.id.analysis_save_energy)
    TextView analysisSaveEnergy;
    @BindView(R.id.analysis_save_money)
    TextView analysisSaveMoney;
    @BindView(R.id.loading_view)
    ProgressBar loadingView;

    private List<String> mSelectList;

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_ANALYSIS;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        AnalysisPresenter analysisPresenter = new AnalysisPresenter();
        return new BasePresenterFactory(this, analysisPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_analysis;
    }

    @Override
    protected void initViewAndEvent(View view) {
        mSelectList = new LinkedList<>(Arrays.asList("day", "week", "month", "year"));
        analysisSpinner.attachDataSource(mSelectList);
        analysisSpinner.setOnItemSelectedListener(this);
        analysisSpinner.addOnItemClickListener(this);
        setChartData();
    }

    private void initChart() {
        analysisChart.setDrawGridBackground(false);
        analysisChart.getDescription().setEnabled(false);
        analysisChart.setDrawBorders(false);

        XAxis xAxis = analysisChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = analysisChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = analysisChart.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = analysisChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
    }

    private void setChartData() {
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
                initChart();
                ArrayList<Entry> e1 = new ArrayList<>();
                ArrayList<Entry> e2 = new ArrayList<>();
                float sumAllBefore = 0.0f;
                float sumAll = 0.0f;
                for (int i = 8; i < 20; i++) {
                    String allEnergy = energies.get(i).getEnergy_all();
                    String allBeforeEnergy = energies.get(i).getBefore_energy_all();
                    String[] energyArray = allEnergy.split(",");
                    String[] energyBeforeArray = allBeforeEnergy.split(",");
                    float sum = 0.0f;
                    float sumBefore = 0.0f;
                    for(int j = 0; j<energyArray.length; j++){
                        sum += Float.valueOf(energyArray[j]);
                    }
                    for(int j = 0; j<energyBeforeArray.length; j++){
                        sumBefore += Float.valueOf(energyBeforeArray[j]);
                    }
                    sumAll += sum;
                    sumAllBefore += sumBefore;
                    e1.add(new Entry(i, sumBefore));
                    e2.add(new Entry(i, sum));
                }
                LineDataSet d1 = new LineDataSet(e1, "改造前能耗");
                d1.setLineWidth(2.5f);
                d1.setCircleRadius(4.5f);
                //Color.rgb(244, 117, 117)
                d1.setHighLightColor(Color.rgb(244, 117, 117));
                d1.setDrawValues(false);

                LineDataSet d2 = new LineDataSet(e2, "改造后能耗");
                d2.setLineWidth(2.5f);
                d2.setCircleRadius(4.5f);
                d2.setHighLightColor(Color.rgb(244, 117, 117));
                //ColorTemplate.VORDIPLOM_COLORS[0]
                d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
                d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
                d2.setDrawValues(false);

                ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
                sets.add(d1);
                sets.add(d2);

                LineData cd = new LineData(sets);
                analysisChart.setData(cd);
                analysisChart.animateXY(3000, 3000);

                analysisBeforeEnergy.setText(String.format("%.2f", sumAllBefore));
                analysisAfterEnergy.setText(String.format("%.2f", sumAll));
                analysisSaveEnergy.setText(String.format("%.2f", sumAllBefore - sumAll));
                analysisSaveMoney.setText(String.format("%.2f", sumAllBefore - sumAll));
            }
        });

        /*for (int i = 0; i < 12; i++) {
            e1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }*/
        /*for (int i = 0; i < 12; i++) {
            e2.add(new Entry(i, e1.get(i).getY() - 30));
        }*/
    }

    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        analysisChart.setVisibility(View.GONE);
    }

    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
        analysisChart.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Snackbar.make(view, "click" + mSelectList.get(position), Snackbar.LENGTH_SHORT).show();
        Log.e("tzy", "select index is:" + analysisSpinner.getSelectedIndex() + "position is" + position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e("tzy", "nothing");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Snackbar.make(view, "click position:" + mSelectList.get(position), Snackbar.LENGTH_SHORT).show();
        Log.e("tzy", "click select index is:" + analysisSpinner.getSelectedIndex() + "position is" + position);
    }
}
