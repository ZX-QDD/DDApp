package com.dongdong.ddapp.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dongdong.ddapp.R

class NormalDialogActivity : AppCompatActivity() {

    companion object {
        const val TAG = "NormalDialogActivity"
        fun launch(context: Context) {
            context.startActivity(Intent(context, NormalDialogActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_dialog)
    }
}