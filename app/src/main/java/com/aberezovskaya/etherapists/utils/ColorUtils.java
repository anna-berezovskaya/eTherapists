package com.aberezovskaya.etherapists.utils;


import android.content.Context;
import android.graphics.Color;

public class ColorUtils {

    public static int dipToPix(Context context, int dp){
        return (int)context.getResources().getDisplayMetrics().density * dp;
    }

    public static boolean closeMatch (int color1, int color2, int tolerance) {
        if ((int) Math.abs (Color.red(color1) - Color.red (color2)) > tolerance )
            return false;
        if ((int) Math.abs (Color.green (color1) - Color.green (color2)) > tolerance )
            return false;
        if ((int) Math.abs (Color.blue (color1) - Color.blue (color2)) > tolerance )
            return false;
        return true;
    }
}
