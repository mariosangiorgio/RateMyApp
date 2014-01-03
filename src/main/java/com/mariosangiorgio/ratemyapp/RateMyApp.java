package com.mariosangiorgio.ratemyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class RateMyApp {
    private final Context context;
    private final PreferencesManager preferencesManager;
    private final int daysUntilPrompt;
    private final int launchesUntilPrompt;

    public RateMyApp(Context context, int daysUntilPrompt, int launchesUntilPrompt){
        if(context == null){
            throw new IllegalArgumentException("context should not be null");
        }
        if(daysUntilPrompt < 0 || launchesUntilPrompt < 0)
        {
            throw new IllegalArgumentException("Expected non-negative values");
        }
        this.context = context;
        this.daysUntilPrompt = daysUntilPrompt;
        this.launchesUntilPrompt = launchesUntilPrompt;
        this.preferencesManager = PreferencesManager.buildFromContext(context);
    }

    public void appLaunched(){
        if(preferencesManager.alertEnabled()){
            if(canShowDialog()){
                showDialog();
            }
            else{
                preferencesManager.incrementLaunchCounter();
            }
        }
    }

    private boolean canShowDialog() {
        return preferencesManager.launchCounter() >= launchesUntilPrompt &&
                preferencesManager.daysFromFirstLaunch() >= daysUntilPrompt;
    }

    private String getApplicationName(Context context){
        return context.getApplicationContext().getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public void showDialog() {
        DialogListener listener = new DialogListener();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.rate)+" "+getApplicationName(context));
        dialogBuilder.setMessage(R.string.rate_message);
        dialogBuilder.setPositiveButton(R.string.rate_button, listener);
        dialogBuilder.setNeutralButton(R.string.later_button, listener);
        dialogBuilder.setNegativeButton(R.string.never_button, listener);
        dialogBuilder.show();
    }

    private class DialogListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int buttonPressed) {
            switch(buttonPressed){
                case DialogInterface.BUTTON_POSITIVE:
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                    preferencesManager.disableAlert();
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    preferencesManager.resetFirstLaunchTimestamp();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    preferencesManager.disableAlert();
                    break;
            }
            dialogInterface.dismiss();
        }
    }
}
