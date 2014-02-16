package com.mariosangiorgio.ratemyapp.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mariosangiorgio.ratemyapp.PreferencesManager;

public class OpenPlayStoreAction extends Action{
    public OpenPlayStoreAction(Context context){
        super(context);
    }

    @Override
    public void execute() {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id" + context.getPackageName())));
        preferencesManager.disableAlert();
    }
}
