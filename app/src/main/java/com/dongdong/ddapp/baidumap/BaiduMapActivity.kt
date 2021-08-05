package com.dongdong.ddapp.baidumap

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.baidu.mapapi.map.MapView
import com.dongdong.ddapp.R


class BaiduMapActivity : Activity() {
    private var mMapView: MapView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baidu_map)
        //获取地图控件引用
        mMapView = findViewById<View>(R.id.bmapView) as MapView
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView?.onDestroy()
    }
}