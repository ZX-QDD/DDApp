package com.dongdong.ddapp.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dongdong.ddapp.R
import com.dongdong.ddapp.widget.activity.RatingActivity
import com.dongdong.ddapp.widget.activity.TestViewActivity
import kotlinx.android.synthetic.main.activity_widget.*

class WidgetActivity : AppCompatActivity() {

    companion object {
        const val TAG = "WidgetActivity"
        fun launch(context: Context) {
            context.startActivity(Intent(context, WidgetActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget)
        initListener()
    }

    private fun initListener() {
        btn_goto_rating.setOnClickListener {
            RatingActivity.launch(this)
        }

        btn_goto_test.setOnClickListener {
            TestViewActivity.launch(this)
        }

    }
}