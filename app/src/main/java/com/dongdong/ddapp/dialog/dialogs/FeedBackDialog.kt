package com.dongdong.ddapp.dialog.dialogs

import com.dongdong.ddapp.R
import android.app.Activity
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dongdong.ddapp.dialog.adapter.FeedbackAdapter
import com.dongdong.ddapp.dialog.adapter.FeedbackItemClickCallback
import com.dongdong.ddapp.dialog.bind

class FeedbackDialog(
    activity: Activity,
    data: List<String>
) : BaseDialog(activity, R.style.FeedbackDialog), FeedbackItemClickCallback{

    private val cancelButton: TextView by bind(R.id.cancel)
    private val sendButton: TextView by bind(R.id.send)
    private val rvOptions: RecyclerView by bind(R.id.rvOptions)
    private val TAG = "FeedbackDialog"
    private val mSelectedOptions: MutableList<String> = mutableListOf()

    init {
        setContentView(R.layout.dialog_feedback)
        //返回键不可取消弹窗
        setCancelable(false)
        //点击弹窗外部不可取消
        setCanceledOnTouchOutside(false)
        val layoutManager = GridLayoutManager(context,3)
        val adapter = FeedbackAdapter(context, this)
        rvOptions.layoutManager = layoutManager
        rvOptions.adapter = adapter
        adapter.setData(data)

//        val disposable = DWApi.getPecadoService(QuitFeedbackApi::class.java)
//            .getResearch()
//            .observeOn(DWSchedulers2.main())
//            .subscribe({ model ->
//                val options = model.options ?: mutableListOf()
//                adapter.setData(model.options ?: emptyList())
//                sendButton.text = model.submitTitle ?: ""
//                Log.d(TAG, "succeed fetch config")
//            }, {
//                Log.d(TAG, "error fetch config")
//            })
        cancelButton.setOnClickListener {
            dismiss()
        }
        sendButton.setOnClickListener {
            dismiss()
//            Observable.timer(300, TimeUnit.MILLISECONDS)
//                .observeOn(DWSchedulers.main())
//                .subscribe {
//                    activity.finish()
//                    dismiss()
//                }
        }
    }

    private fun ensureDisableSendButtonWhenNoFeedback() {
        val enabled = mSelectedOptions.size > 0
        sendButton.isEnabled = enabled
        if (enabled) {
            sendButton.alpha = 1F
        } else {
            sendButton.alpha = 0.5F
        }
    }

    override fun callback(option: String, checked: Boolean) {
        if (checked) {
            mSelectedOptions.add(option)
        } else {
            mSelectedOptions.remove(option)
        }
        ensureDisableSendButtonWhenNoFeedback()
    }
}