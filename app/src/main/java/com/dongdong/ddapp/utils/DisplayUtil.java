package com.dongdong.ddapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class DisplayUtil {

    private static Integer displayWidth = null;
    private static Integer displayHeight = null;

    public static int getDisplayWidth(Context context) {
        if (displayWidth == null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayWidth = size.x;
            displayHeight = size.y;
        }
        return displayWidth;
    }

    @Deprecated
    public static int getDisplayHeight(Context context) {
        if (displayHeight == null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayWidth = size.x;
            displayHeight = size.y;
        }
        return displayHeight;
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        if (context == null) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getScreenHeight(@NonNull Context context) {
        return getScreenHeight() + getMiSupplementHeight(context);
    }

    public static int getRootLeft(View myView) {
        if (myView.getParent() == myView.getRootView()) {
            return myView.getLeft();
        } else {
            return myView.getLeft() + getRootLeft((View) myView.getParent());
        }
    }

    public static int getRootTop(View myView) {
        if (myView.getParent() == myView.getRootView()) {
            return myView.getTop();
        } else {
            return myView.getTop() + getRootTop((View) myView.getParent());
        }
    }

    private static int getMiSupplementHeight(Context context) {
        int result = 0;
        if (isXiaomi()) {
            if (Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) == 0) {
                //如果虚拟按键已经显示，则不需要补充高度
                return result;
            } else {
                //如果虚拟按键没有显示，则需要补充虚拟按键高度到屏幕高度
                Resources res = context.getResources();
                int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    result = res.getDimensionPixelSize(resourceId);
                }
            }
        }

        return result;
    }

    static boolean isXiaomi() {
        String device = Build.MANUFACTURER;
//        CenterLog.d(TAG, "Build.MANUFACTURER = " + device);
        return device.equals("Xiaomi");
    }
}

