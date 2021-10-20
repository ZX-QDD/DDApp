package com.dongdong.ddapp.dialog.dialogs

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.dongdong.ddapp.R
import com.dongdong.ddapp.dialog.bind

class BindPhoneDialog(context: Context) : BaseDialog(context, R.style.FeedbackDialog ){

    private val bindButton: TextView by bind(R.id.btnBindPhone)
    private val cancelButton: ImageView by bind(R.id.imgClose)

    init {
        setContentView(R.layout.dialog_bind_phone)
        //返回键不可取消弹窗
        setCancelable(false)
        //点击弹窗外部不可取消
        setCanceledOnTouchOutside(false)
        bindButton.setOnClickListener {
            //跳转到phone界面
            dismiss()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }
}