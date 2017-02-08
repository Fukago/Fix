package com.skylinetan.energycloud.ui.adapter;

import android.content.Context;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.AirCondition;
import com.skylinetan.energycloud.bean.AirCondition1;

import java.util.List;

/**
 * Created by skylineTan on 16/12/12.
 */
public class AirConditionAdapter extends BaseRecyclerAdapter<AirCondition>{

    public AirConditionAdapter(Context context, int layoutResId, List<AirCondition> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void bindData(BaseViewHolder viewHolder, AirCondition item, int position) {
        viewHolder.setText(R.id.text_info_name, item.getTextInfoName())
                .setText(R.id.text_info_num, item.getTextInfoNum());
    }
}
