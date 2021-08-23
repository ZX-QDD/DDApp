package com.dongdong.ddapp.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class TestView extends View {
    private static final String TAG = "TestView";
    private int mLastX;
    private int mLastY;

    //定义画笔
    private Paint mPaint = new Paint();

    /**
     * 实现构造方法
     *
     * @param context
     */
//    public TestView(Context context) {
//        super(context);
////        init();
//    }
    Scroller scroller = new Scroller(getContext());

    private void smoothScrollTo(int destX,int destY) {
        int scrollX = getScrollX();
        int delta = destX -scrollX;
        // 1000ms内滑向destX，效果就是慢慢滑动
        scroller.startScroll(scrollX,0,delta,0,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x -mLastX;
                int deltaY = y -mLastY;
                Log.d(TAG,"move,deltaX:" + deltaX + " deltaY:" + deltaY);
                int translationX = (int)getTranslationX() + deltaX;
                int translationY = (int)getTranslationY() + deltaY;
                setTranslationX(translationX);
                setTranslationY(translationY);
                break;
            }
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

//    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
////        init();
//    }

    private void init() {
        mPaint.setColor(Color.BLUE);

    }
//
//    /**
//     * 重写onMeasure方法
//     *
//     * @param widthMeasureSpec
//     * @param heightMeasureSpec
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, 300);
//        } else if (widthMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(300, heightSize);
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(widthSize, 300);
//        }
//    }

    /**
     * 重写draw方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        //获取各个编剧的padding值
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        //获取绘制的View的宽度
        int width = getWidth() - paddingLeft - paddingRight;
        //获取绘制的View的高度
        int height = getHeight() - paddingTop - paddingBottom;
        smoothScrollTo(width, height);
        //绘制View，左上角坐标（0+paddingLeft,0+paddingTop），右下角坐标（width+paddingLeft,height+paddingTop）
        canvas.drawRect(0 + paddingLeft, 0 + paddingTop, width + paddingLeft, height + paddingTop, mPaint);
    }
}