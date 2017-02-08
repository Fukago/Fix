package com.skylinetan.energycloud.ui.adapter;

import android.content.Context;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.AirCondition;
import com.skylinetan.energycloud.bean.MapInfo;

import java.util.List;

/**
 * Created by skylineTan on 16/12/12.
 */
public class MapInfoAdapter extends BaseRecyclerAdapter<MapInfo>{

    public MapInfoAdapter(Context context, int layoutResId, List<MapInfo> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void bindData(BaseViewHolder viewHolder, MapInfo item, int position) {
        viewHolder.setText(R.id.map_info_name, item.getMapInfoName())
                .setText(R.id.map_info_num, item.getMapInfoNum());
    }
}
