package com.dongdong.ddapp.widget.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dongdong.ddapp.R
import kotlinx.android.synthetic.main.activity_test_view.*

class TestViewActivity : AppCompatActivity() {

    companion object {
        const val TAG = "TestViewActivity"
        fun launch(context: Context) {
            context.startActivity(Intent(context, TestViewActivity::class.java))
        }
    }

    private var radius = 0.1F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_view)
        packageProgressView.progress = radius
        packageProgressView.setOnClickListener {
            radius += 0.1F
            packageProgressView.progress = radius
        }
        packageProgressView.setOnLongClickListener {
            radius = 0F
            packageProgressView.progress = radius
            true
        }
        test.setOnLongClickListener {
            true
        }
    }

}