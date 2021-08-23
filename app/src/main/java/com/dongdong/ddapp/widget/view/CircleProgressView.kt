package com.dongdong.ddapp.widget.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.dongdong.ddapp.R

class CircleProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val backgroundPaint: Paint
    private val progressPaint: Paint

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView)

        backgroundPaint = Paint().also {
            it.color = typedArray.getColor(
                R.styleable.CircleProgressView_backgroundColor,
                ContextCompat.getColor(context, R.color.yellow)
            )
            it.alpha = (0.2F * 255).toInt()
            it.isAntiAlias = true //抗锯齿
            it.style = Paint.Style.FILL
        }
        progressPaint = Paint().also {
            it.color = typedArray.getColor(
                R.styleable.CircleProgressView_progressColor,
                ContextCompat.getColor(context, R.color.yellow)
            )
            it.isAntiAlias = true
            it.style = Paint.Style.FILL
        }
        typedArray.recycle() //回收typedArray
    }

    var progress: Float = if (isInEditMode) 0.5F else 0F
        set(value) {
            field = value
            invalidate()
        }

    fun backgroundPaintColor(color: Int) {
        backgroundPaint.color = color
        invalidate()
    }

    fun progressPaintColor(color: Int) {
        progressPaint.color = color
        invalidate()
    }

    private val rect = RectF()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        canvas?.translate((width / 2).toFloat(), (height / 2).toFloat())
        canvas?.drawCircle(0F, 0F, (width / 2).toFloat(), backgroundPaint)
        canvas?.restore()
        rect.set(0.toFloat(), 0.toFloat(), width.toFloat(), height.toFloat())
        canvas?.drawArc(rect, -90F, 360F * progress, true, progressPaint)
    }
}