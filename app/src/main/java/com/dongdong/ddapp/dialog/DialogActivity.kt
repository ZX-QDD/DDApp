package com.dongdong.ddapp.dialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dongdong.ddapp.R
import com.dongdong.ddapp.dialog.dialogs.BindPhoneDialog
import com.dongdong.ddapp.dialog.dialogs.FeedbackDialog
import com.dongdong.ddapp.dialog.dialogs.NormalDialog
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity() {

    companion object {
        val TAG = "DialogActivity"
        fun launch(context: Context) {
            context.startActivity(Intent(context, DialogActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        initView()
        initListener()
    }

    private fun initView() {
        btnShowNormalDialog.alpha = 0f
        btnShowNormalDialog.animate().alpha(1F).duration = 500
    }

    private fun initListener() {
        btnShowNormalDialog.setOnClickListener {
            showNormalDialog()
        }
        btnShowFeedbackDialog.setOnClickListener {
            showFreedBackDialog()
        }
        btnShoBindPhoneDialog.setOnClickListener {
            showBindPhoneDialog()
        }
    }

    private fun showNormalDialog() {
        val builder = NormalDialog(this)
        builder.setCancelable(false)
            .setTitle("一个普通的弹窗")
            .setNegativeButton("取消") { _, _ -> }
            .setPositiveButton("确定") { _, _ -> }
            .show()
    }

    private fun showFreedBackDialog() {
        val data = mutableListOf("没时间", "内容枯燥", "讲解无聊", "网卡顿", "课程将结束", "其他")
        val dialog = FeedbackDialog(this, data)
        dialog.show()
    }

    private fun showBindPhoneDialog() {
        BindPhoneDialog(this).show()
    }
}