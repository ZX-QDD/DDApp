package com.dongdong.ddapp.widget.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dongdong.ddapp.R
import com.dongdong.ddapp.widget.view.RatingBar
import kotlinx.android.synthetic.main.activity_rating.*

class RatingActivity : AppCompatActivity() {

    companion object {
        val TAG = "RatingActivity"
        fun launch(context: Context) {
            context.startActivity(Intent(context, RatingActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)
        ratingBar.isClickable = true //设置可否点击
        ratingBar.setStar(2f) //设置显示的星星个数
        ratingBar.setStepSize(RatingBar.StepSize.Full) //设置每次点击增加一颗星还是半颗星

        ratingBar.setOnRatingChangeListener { ratingCount ->
            Log.d(TAG, "RatingBar-Count=$ratingCount")
        }
    }
}