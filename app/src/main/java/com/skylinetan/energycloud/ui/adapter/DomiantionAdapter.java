package com.skylinetan.energycloud.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.Domiantion;
import com.skylinetan.energycloud.ui.activity.OrderActivity;

import java.util.List;

/**
 * Created by apple on 2017/2/9.
 */

public class DomiantionAdapter extends BaseRecyclerAdapter<Domiantion> {
    private Context mContext;

    public DomiantionAdapter(Context context, int layoutResId, List<Domiantion> data) {
        super(context, layoutResId, data);
        mContext = context;
    }

    @Override
    protected void bindData(BaseViewHolder viewHolder, final Domiantion item, int position) {
        viewHolder.setText(R.id.tv_id, "设备ID：" + item.getId() + "")
                .setText(R.id.tv_address, "设备地址：" + item.getAddress() + "")
                .setText(R.id.tv_equipment_name, "详细地址：" + item.getEquipment_name())
                .setText(R.id.tv_name, "设备名称：" + item.getName() + "")
                .setText(R.id.tv_status_domiantion, item.getOperating_status() + "");
        switch (item.getEquipment_type()) {
            case "动力":
                viewHolder.setImageResource(R.id.im_type_domiantion, R.mipmap.main_home);
                break;
            case "空调":
                viewHolder.setImageResource(R.id.im_type_domiantion, R.mipmap.main_user);
                break;
            case "照明":
                viewHolder.setImageResource(R.id.im_type_domiantion, R.mipmap.main_monitor);
                break;
            case "特殊":
                viewHolder.setImageResource(R.id.im_type_domiantion, R.mipmap.ic_login_phone);
                break;
        }
        switch (item.getOperating_status()) {
            case 1:
                viewHolder.setText(R.id.tv_status_domiantion, "使用中");
                break;
            case 2:
                viewHolder.setText(R.id.tv_status_domiantion, "损坏中");
                break;
            case 3:
                viewHolder.setText(R.id.tv_status_domiantion, "修理中");
                break;
            default:
                viewHolder.setText(R.id.tv_status_domiantion, "使用中");
        }
        viewHolder.setOnClickListener(R.id.rl_domiantion, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, OrderActivity.class);
                it.putExtra("user_id",""+item.getUser_id());
                it.putExtra("equipment_id",""+item.getEquipment_id());
                it.putExtra("equipment_address",""+item.getEquipment_id());
                it.putExtra("name",""+item.getName());
                it.putExtra("equipment_name",""+item.getEquipment_name());
                mContext.startActivity(it);
            }
        });
    }
}
