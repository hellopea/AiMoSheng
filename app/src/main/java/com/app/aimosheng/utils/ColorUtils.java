package com.app.aimosheng.utils;

import android.graphics.Color;

public class ColorUtils {
    public static int getDarkerColor(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv
        // make darker
        hsv[1] = hsv[1] + 0.1f; // 饱和度更高
        hsv[2] = hsv[2] - 0.1f; // 明度降低
        int darkerColor = Color.HSVToColor(hsv);
        return  darkerColor ;
    }
    // 获取更浅的颜色
    public static int getBrighterColor(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv

        hsv[1] = hsv[1] - 0.1f; // less saturation
        hsv[2] = hsv[2] + 0.1f; // more brightness
        int darkerColor = Color.HSVToColor(hsv);
        return  darkerColor ;
    }

}
