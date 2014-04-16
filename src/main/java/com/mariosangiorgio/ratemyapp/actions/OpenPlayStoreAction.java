package com.mariosangiorgio.ratemyapp.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mariosangiorgio.ratemyapp.PreferencesManager;

public class OpenPlayStoreAction implements Action{
    private final Context context;
    private final PreferencesManager preferencesManager;

    public OpenPlayStoreAction(Context context, PreferencesManager preferencesManager){
        if(context == null){
            throw new IllegalArgumentException();
        }
        this.context = context;
        if(preferencesManager == null){
            throw new IllegalArgumentException();
        }
        this.preferencesManager = preferencesManager;
    }

    @Override
    public void execute() {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
        preferencesManager.disableAlert();
    }
}
