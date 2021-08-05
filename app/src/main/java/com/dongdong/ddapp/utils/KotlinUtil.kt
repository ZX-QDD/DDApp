package com.dongdong.ddapp.utils

import android.content.res.Resources
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.ViewGroup
import java.lang.reflect.Proxy
import java.math.RoundingMode
import java.text.DecimalFormat


//fun Int.getResString() = DWApplicationContext.getString(this)
//fun Int.getResString(vararg formatArgs: Any) = DWApplicationContext.getString(this, formatArgs)

private fun density() = Resources.getSystem().displayMetrics.density
fun Number.toPx() = this.toFloat() * density() + 0.5f
fun Number.toDp() = this.toFloat() / density()
fun Number.toPxInt() = (this.toFloat() * density() + 0.5f).toInt()
fun Number.toDpInt() = (this.toFloat() / density()).toInt()

fun Int.getFormatDuration(): String {
    val hour = this / 3600
    val minute = this % 3600 / 60
    val second = this % 60
    return "${if (hour > 0) "$hour:" else ""}${if (minute < 10) "0$minute:" else "$minute:"}${if (second < 10) "0$second" else "$second"}"
}

fun Int.formatCountFloor(): String {
    return if (this < 10000) {
        "$this"
    } else {
        val result = this.div(10000f)
        val format = DecimalFormat()
        format.maximumFractionDigits = 1
        format.groupingSize = 0
        format.roundingMode = RoundingMode.FLOOR
        "${format.format(result)}w"
    }
}

fun Boolean?.toLog(): Int {
    return if (this == true) 1 else 0
}

fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
    if (value1 != null && value2 != null) {
        bothNotNull(value1, value2)
    }
}

private val HEX_CHARS = "0123456789abcdef".toCharArray()

fun ByteArray.toHex(): String {
    val result = StringBuffer()

    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }

    return result.toString()
}

fun View.updateMargin(
    layout: ViewGroup.MarginLayoutParams = (layoutParams as ViewGroup.MarginLayoutParams),
    left: Int = layout.leftMargin,
    top: Int = layout.topMargin,
    right: Int = layout.rightMargin,
    bottom: Int = layout.bottomMargin
) {
    (layoutParams as ViewGroup.MarginLayoutParams).setMargins(left, top, right, bottom)
}

fun SpannableString.bold(string: String) {
    val origin = this.toString()
    val start = origin.indexOf(string)
    if (start == -1) {
        return
    }
    setSpan(StyleSpan(Typeface.BOLD), start, start + string.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}

fun SpannableString.underLine(string: String) {
    val origin = this.toString()
    val start = origin.indexOf(string)
    if (start == -1) {
        return
    }
    setSpan(UnderlineSpan(), start, start + string.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}

fun SpannableString.onClick(string: String, color: Int, onClickListener: View.OnClickListener?) {
    val origin = this.toString()
    val start = origin.indexOf(string)
    if (start == -1) {
        return
    }
    setSpan(object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = color
            ds.clearShadowLayer()
        }

        override fun onClick(widget: View) {
            onClickListener?.onClick(widget)
        }
    }, start, start + string.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}

inline fun <reified T : Any> emptyImpl(): T {
    return T::class.java.let {
        Proxy.newProxyInstance(
            it.classLoader,
            arrayOf(it)
        ) { _, _, _ -> } as T
    }
}