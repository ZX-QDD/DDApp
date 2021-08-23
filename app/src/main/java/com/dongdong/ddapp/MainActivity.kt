package com.dongdong.ddapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dongdong.ddapp.baidumap.BaiduMapActivity
import com.dongdong.ddapp.camreax.CameraActivity
import com.dongdong.ddapp.dialog.DialogActivity
import com.dongdong.ddapp.recycle.SomeDataListActivity
import com.dongdong.ddapp.widget.WidgetActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
    }

    private fun initListener() {
        btn_goto_dialog.setOnClickListener {
            DialogActivity.launch(this)
        }

        btn_goto_recycle.setOnClickListener {
            SomeDataListActivity.launch(this)
        }

        btn_goto_baidumap.setOnClickListener {
            BaiduMapActivity.launch(this)
        }

        btn_goto_camera.setOnClickListener {
            CameraActivity.launch(this)
        }

        btn_goto_widget.setOnClickListener {
            WidgetActivity.launch(this)
        }
    }
}