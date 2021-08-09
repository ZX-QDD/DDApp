package com.dongdong.ddapp.dialog

import android.app.Dialog
import android.os.Build
import android.view.*
import androidx.annotation.IdRes
import com.dongdong.ddapp.dialog.dialogs.BaseDialog

fun BaseDialog.showOnBottom() {
    showOnBottom(ViewGroup.LayoutParams.WRAP_CONTENT)
}

/**
 * 在底部显示dialog
 */
fun BaseDialog.showOnBottom(windowHeight: Int) {
    show()
    val dm = context.applicationContext.resources.displayMetrics
    val params = window?.attributes
    params?.width = dm.widthPixels
    params?.height = windowHeight
    window?.attributes = params
    window?.setGravity(Gravity.BOTTOM)
}

fun <T : View> Dialog.bind(@IdRes res: Int): Lazy<T> {
    return lazy { findViewById<T>(res) }
}

fun Dialog.fitAndroidPNotch() {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    window?.displayCutout()
    window?.fullScreen()
}

fun Window.matchParent() {
    setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT
    )
}

fun Window.displayCutout() {
    if (Build.VERSION.SDK_INT >= 28) {
        val lp = attributes
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams
            .LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        attributes = lp
    }
}

fun Window.fullScreen() {
    var systemUiVisibility = decorView.systemUiVisibility
    val flags = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN)
    systemUiVisibility = systemUiVisibility or flags
    decorView.systemUiVisibility = systemUiVisibility
}

fun Window.touchAndModal() {
    addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
    addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}