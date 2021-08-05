package com.dongdong.ddapp.dialoglearn

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.dongdong.ddapp.R
import com.dongdong.ddapp.dialoglearn.dialog.FeedbackDialog
import com.dongdong.ddapp.dialoglearn.dialog.NormalDialog
import kotlinx.android.synthetic.main.dialog_activity_dialog.*


class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_activity_dialog)
        btnShowDialog.alpha = 1f
        btnShowDialog.animate().alpha(0F).duration = 500
        btnShowDialog.visibility = View.GONE
        btnShowDialog.setOnClickListener {
            showFreedBackDialog()
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
//        dialog.showOnCenter()
    }
}