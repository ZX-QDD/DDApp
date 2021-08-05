package com.dongdong.ddapp.dialoglearn.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.ViewGroup

open class BaseDialog(private var mContext: Context, style: Int) : Dialog(mContext, style) {

    protected val isAttachedActivityFinishing: Boolean
        get() = mContext is Activity && ((mContext as Activity).isFinishing || (mContext as Activity).isDestroyed)

    /**
     * 调整dialog宽度
     */
    override fun show() {
        if (isAttachedActivityFinishing) {
            // may async invoke by callback
            return
        }
        super.show()
        val dm = mContext.applicationContext.resources.displayMetrics
        val params = window!!.attributes
        params.width = dm.widthPixels
        window!!.attributes = params
    }

    protected fun matchParent() {
        val attributes = window?.attributes
        attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
        attributes?.height = ViewGroup.LayoutParams.MATCH_PARENT
        window?.attributes = attributes
    }
}
