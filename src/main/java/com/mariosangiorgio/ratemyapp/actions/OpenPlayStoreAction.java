package com.mariosangiorgio.ratemyapp.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class OpenPlayStoreAction extends Action{
    public OpenPlayStoreAction(Context context){
        super(context);
    }

    @Override
    public void execute() {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
        preferencesManager.disableAlert();
    }
}
