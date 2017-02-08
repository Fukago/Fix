package com.skylinetan.energycloud.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.AirCondition;
import com.skylinetan.energycloud.bean.AirCondition1;
import com.skylinetan.energycloud.presenter.BasePresenterFactory;
import com.skylinetan.energycloud.presenter.PresenterFactory;
import com.skylinetan.energycloud.presenter.impl.LoginPresenter;
import com.skylinetan.energycloud.presenter.impl.TextInfoPresenter;
import com.skylinetan.energycloud.support.Constants;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.ui.adapter.AirConditionAdapter;
import com.skylinetan.energycloud.view.ITextInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * Created by skylineTan on 16/12/12.
 */
public class TextInfoFragment extends SilBaseFragment<TextInfoPresenter> implements ITextInfoView {

    @BindView(R.id.text_info_recycler_view)
    RecyclerView textInfoRecyclerView;

    private AirConditionAdapter mAirConditionAdapter;

    @Override
    protected int getLoaderId() {
        return Constants.LOAD_ID_TEXT_INFO;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PresenterFactory createPresenterFactory() {
        TextInfoPresenter textInfoPresenter = new TextInfoPresenter();
        return new BasePresenterFactory(this, textInfoPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_text_info;
    }

    @Override
    protected void initViewAndEvent(View view) {
        final List<AirCondition> airConditionList = new ArrayList<>();
        mAirConditionAdapter = new AirConditionAdapter(getContext(), R.layout.item_text_info, airConditionList);
        textInfoRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        textInfoRecyclerView.setAdapter(mAirConditionAdapter);
        RequestManager.getInstance().getAircondition(new Subscriber<List<AirCondition1>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("tzy", "error:"+e.getMessage());
            }

            @Override
            public void onNext(List<AirCondition1> airCondition1s) {
                airConditionList.add(new AirCondition("回水温度 ", String.format("%.2f", airCondition1s.get(0).getIn_temp())));
                airConditionList.add(new AirCondition("出水温度 ", String.format("%.2f", airCondition1s.get(0).getOut_temp())));
                airConditionList.add(new AirCondition("冷冻水流量 ", String.format("%.2f", airCondition1s.get(0).getWaterflow())));
                airConditionList.add(new AirCondition("主机功耗 ", String.format("%.2f", airCondition1s.get(0).getHost_power())));
                airConditionList.add(new AirCondition("制冷量 ", String.format("%.2f", airCondition1s.get(0).getCooling_capacity())));
                airConditionList.add(new AirCondition("COP ", String.format("%.2f", airCondition1s.get(0).getCop())));
                airConditionList.add(new AirCondition("冷却塔1功耗 ", String.format("%.2f", airCondition1s.get(0).getPump1_power())));
                airConditionList.add(new AirCondition("冷却塔2功耗 ", String.format("%.2f", airCondition1s.get(0).getPump2_power())));
                airConditionList.add(new AirCondition("冷冻泵1功耗 ", String.format("%.2f", airCondition1s.get(0).getTower1_power())));
                airConditionList.add(new AirCondition("冷冻泵2功耗 ", String.format("%.2f", airCondition1s.get(0).getTower2_power())));
                mAirConditionAdapter.notifyDataSetChanged();
            }
        }, "1");
    }

}
