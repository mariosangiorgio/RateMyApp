package com.mariosangiorgio.ratemyapp;

import android.content.Context;

public class ContextUtils {
    public static String getApplicationName(Context context){
        return context.getApplicationContext().getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }
}
