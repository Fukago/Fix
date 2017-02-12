package com.skylinetan.energycloud.ui.adapter;

import android.content.Context;

import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.Order;

import java.util.List;

/**
 * Created by apple on 2017/2/12.
 */

public class OrderAdapter extends BaseRecyclerAdapter<Order> {
    private Context mContext;

    public OrderAdapter(Context context, int layoutResId, List<Order> data) {
        super(context, layoutResId, data);
        mContext = context;
    }

    @Override
    protected void bindData(BaseViewHolder viewHolder, Order item, int position) {
        viewHolder.setText(R.id.tv_order_id, "订单号：" + item.getRepairlist_id() + "")
                .setText(R.id.tv_id, "设备号：" + item.getEquipment_id())
                .setText(R.id.tv_equipment_name, "设备名称：" + item.getEquipment_name())
                .setText(R.id.tv_create_time, "创建时间：" + item.getCreated_at());
        if (item.getPhone() == null || item.getPhone().isEmpty())
            viewHolder.setText(R.id.tv_repair_phone, "暂时无人接单");
        else
            viewHolder.setText(R.id.tv_repair_phone, "联系人电话" + item.getPhone());
        switch (item.getEquipment_type()) {
            case "动力":
                viewHolder.setImageResource(R.id.im_type_order, R.mipmap.main_home);
                break;
            case "空调":
                viewHolder.setImageResource(R.id.im_type_order, R.mipmap.main_user);
                break;
            case "照明":
                viewHolder.setImageResource(R.id.im_type_order, R.mipmap.main_monitor);
                break;
            case "特殊":
                viewHolder.setImageResource(R.id.im_type_order, R.mipmap.ic_login_phone);
                break;
        }
        switch (item.getStatus()) {
            case "1":
                viewHolder.setText(R.id.tv_status_order, "使用中");
                break;
            case "2":
                viewHolder.setText(R.id.tv_status_order, "损坏中");
                break;
            case "3":
                viewHolder.setText(R.id.tv_status_order, "修理中");
                break;
            default:
                viewHolder.setText(R.id.tv_status_order, "使用中");
        }
    }
}
