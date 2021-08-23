package com.dongdong.ddapp.widget.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller

class TestViewKt @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val TAG: String = "TestViewKt"
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mLastX = 0
    private var mLastY = 0

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }

    var scroller = Scroller(getContext())

    private fun smoothScrollTo(destX: Int, destY: Int) {
        val scrollX = scrollX
        val delta = destX - scrollX
        // 1000ms内滑向destX，效果就是慢慢滑动
        scroller.startScroll(scrollX, 0, delta, 0, 1000)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX: Int = x - mLastX
                val deltaY: Int = y - mLastY
                Log.d(TAG, "move,deltaX:$deltaX deltaY:$deltaY")
                val translationX = translationX.toInt() + deltaX
                val translationY = translationY.toInt() + deltaY
                setTranslationX(translationX.toFloat())
                setTranslationY(translationY.toFloat())
            }
            else -> {
            }
        }
        mLastX = x
        mLastY = y
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20F

        canvas?.translate((width / 2).toFloat(), (height / 2).toFloat())
        canvas?.drawCircle(0f, 0f, (width / 4).toFloat(), paint)

//        canvas?.drawPath()
//        canvas?.drawCircle(300F, 300F, 200F, paint)
    }

}