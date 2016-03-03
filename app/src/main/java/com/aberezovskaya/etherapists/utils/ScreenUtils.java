package com.aberezovskaya.etherapists.utils;


import android.content.Context;

public class ScreenUtils {

    public static int dipToPix(Context context, int dp){
        return (int)context.getResources().getDisplayMetrics().density * dp;
    }
}
