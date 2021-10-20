package com.dongdong.ddapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class AndroidBug5497Workaround {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private Activity activity;
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private AndroidBug5497Workaround(Activity activity) {
        this.activity = activity;
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();

            // this judgment is to solve the previous version of 19 do not support immersive status bar result in an
            // incomplete display layout issues
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                if (isShowNav(activity)) {
                    // keyboard probably just became hidden
                    frameLayoutParams.height = usableHeightSansKeyboard - getNavigationBarHeight(activity);
                } else {
                    // keyboard probably just became hidden
                    frameLayoutParams.height = usableHeightSansKeyboard;
                }
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int getStatusBarHeight() {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    private int computeUsableHeight() {
        int statusBarHeight = getStatusBarHeight();

        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);

        // this judgment is to solve the release after 19 when the pop-up soft keyboard, there is a problem between
        // the keyboard and the black areas pushed up the layout (adjustResize)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return (r.bottom - r.top) + statusBarHeight;
        }

        return (r.bottom - r.top);
    }

    private int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * @return 是否显示底部导航
     */
    public boolean isShowNav(Activity activity) {
        boolean flag = false;
        View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (null != content) {
            WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            if (!isLandscape(activity)) {
                int bottom = content.getBottom();// 页面的底部
                if (bottom != point.y) {
                    flag = true;
                }
            } else {
                int right = content.getRight();
                if (right != point.y) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public boolean isLandscape(Activity activity) {
        return activity.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

}

