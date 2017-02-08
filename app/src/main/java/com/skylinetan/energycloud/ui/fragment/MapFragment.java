package com.skylinetan.energycloud.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.skylinetan.energycloud.R;
import com.skylinetan.energycloud.bean.Building;
import com.skylinetan.energycloud.bean.MapInfo;
import com.skylinetan.energycloud.support.network.RequestManager;
import com.skylinetan.energycloud.support.widget.ExpandableLinearLayout;
import com.skylinetan.energycloud.ui.adapter.EquipmentAdapter;
import com.skylinetan.energycloud.ui.adapter.MapInfoAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Subscriber;

/**
 * Created by skylineTan on 16/12/8.
 */
public class MapFragment extends Fragment{

    private static final LatLng GEO_MAIN = new LatLng(29.516799, 106.580319);

    private MapView mMapView;
    private ExpandableLinearLayout mExpandableLinearLayout;
    //private ExpandableLinearLayout mBasicLinearLayout;
    private ImageView mImageView;
    private TextView mTextView;
    //private ImageView mBasicImageView;
    //private TextView mBasicTextView;

    public BaiduMap getBaiduMap(){
        return mMapView == null ? null : mMapView.getMap();
    }

    public MapView getMapView(){
        return mMapView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mMapView = (MapView) view.findViewById(R.id.main_mapView);
        //mExpandableLinearLayout = (ExpandableLinearLayout) view.findViewById(R.id.expandable_linear_layout);
        //mBasicLinearLayout = (ExpandableLinearLayout) view.findViewById(R.id.basic_linear_layout);
        //mImageView = (ImageView) view.findViewById(R.id.main_more_iv);
        //mTextView = (TextView) view.findViewById(R.id.main_more_tv);
        //mBasicImageView = (ImageView) view.findViewById(R.id.main_basic_more_iv);
        //mBasicTextView = (TextView) view.findViewById(R.id.main_basic_more_tv);
        MapStatus.Builder builder = new MapStatus.Builder();
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("x") && intent.hasExtra("y")) {
            // 当用intent参数时，设置中心点为指定点
            Bundle b = intent.getExtras();
            LatLng p = new LatLng(b.getDouble("y"), b.getDouble("x"));
            builder.target(p);
        }else {
            builder.target(GEO_MAIN);
        }
        builder.zoom(17);
        getBaiduMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final List<MapInfo> mapInfoList = new ArrayList<>();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_gcoding);
        OverlayOptions options = new MarkerOptions()
                .position(GEO_MAIN)
                .icon(bitmap);
        getBaiduMap().addOverlay(options);
        getBaiduMap().setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_gcoding);
                OverlayOptions options = new MarkerOptions()
                        .position(latLng)
                        .icon(bitmap);
                getBaiduMap().addOverlay(options);
                RequestManager.getInstance().buildingSearch(new Subscriber<Building>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Building building) {
                        mapInfoList.add(new MapInfo("名称：",building.getName()));
                        mapInfoList.add(new MapInfo("地址：", building.getAddress()));
                        mapInfoList.add(new MapInfo("面积：", building.getArea() + ""));
                        mapInfoList.add(new MapInfo("类型：", building.getType() + ""));
                        mapInfoList.add(new MapInfo("人数：", building.getUser_num() + ""));
                        mapInfoList.add(new MapInfo("今日用电：", building.getD_value() + ""));
                        mapInfoList.add(new MapInfo("本月用电：", building.getE_value() + ""));
                        mapInfoList.add(new MapInfo("上月用电：", "120358.5"));
                        final MapInfoAdapter mapInfoAdapter = new MapInfoAdapter(getActivity(), R.layout.item_map_info, mapInfoList);
                        //调用model层请求显示信息
                        new MaterialDialog.Builder(getActivity())
                                .title("基本信息")
                                .titleColor(getResources().getColor(R.color.colorAccent))
                                .adapter(mapInfoAdapter,new LinearLayoutManager(getActivity()))
                                .positiveText("Certain")
                                .negativeText("Cancel")
                                .show();
                    }
                });
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        /*mExpandableLinearLayout.setOnECViewListener(new ExpandableLinearLayout.OnExpandCollapseViewClickListener() {
            @Override
            public void onClick(View view, boolean isCollapsed) {
                //如果是正在收缩
                if(isCollapsed) {
                    mImageView.setImageResource(R.mipmap.ic_collapse);
                    mTextView.setText("更多");
                }else {
                    mImageView.setImageResource(R.mipmap.ic_expand);
                    mTextView.setText("收起");
                }
            }
        });*/
        /*mBasicLinearLayout.setOnECViewListener(new ExpandableLinearLayout.OnExpandCollapseViewClickListener() {
            @Override
            public void onClick(View view, boolean isCollapsed) {
                //如果是正在收缩
                if(isCollapsed) {
                    mBasicImageView.setImageResource(R.mipmap.ic_collapse);
                    mBasicTextView.setText("更多");
                }else {
                    mBasicImageView.setImageResource(R.mipmap.ic_expand);
                    mBasicTextView.setText("收起");
                }
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
    }
}
