package com.skylinetan.energycloud.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.Equipment;

import java.util.List;

/**
 * Created by skylineTan on 16/12/11.
 */
public class EquipmentAdapter extends BaseRecyclerAdapter<Equipment>{

    public EquipmentAdapter(Context context, int layoutResId, List<Equipment> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void bindData(BaseViewHolder viewHolder, Equipment item, int position) {
        viewHolder.setText(R.id.equipment_id, item.getEquipment_id() + "")
                .setText(R.id.equipment_name, item.getEquipment_name())
                .setText(R.id.warning_hour, item.getWarning_hour() + "")
                .setText(R.id.operating_state, item.getOperating_status() + "")
                .setText(R.id.energy_hour, item.getDvalue() + "");
        if(item.getOperating_status() == 1)
            viewHolder.setText(R.id.operating_state, "True");
        else
            viewHolder.setText(R.id.operating_state, "False");

        if(position % 2 == 0){
            viewHolder.setBackgroundColor(R.id.equipment_bg, ContextCompat.getColor(mContext, R.color.blue_light));
        }else {
            viewHolder.setBackgroundColor(R.id.equipment_bg, ContextCompat.getColor(mContext, R.color.white));
        }
    }
}
