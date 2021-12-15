package com.dongdong.ddapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dongdong.ddapp.baidumap.BaiduMapActivity
import com.dongdong.ddapp.camreax.CameraActivity
import com.dongdong.ddapp.dialog.DialogActivity
import com.dongdong.ddapp.fragment.HomeActivity
import com.dongdong.ddapp.recycle.SomeDataListActivity
import com.dongdong.ddapp.widget.WidgetActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        fun launch(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

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

        btn_goto_home.setOnClickListener {
            HomeActivity.launch(this)
        }
    }
}