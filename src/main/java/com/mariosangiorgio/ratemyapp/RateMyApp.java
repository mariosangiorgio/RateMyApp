package com.mariosangiorgio.ratemyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class RateMyApp implements  NotificationManager{
    private final Context context;
    private final PreferencesManager preferencesManager;
    private final OptionalValue<Integer> daysUntilPrompt;
    private final OptionalValue<Integer> launchesUntilPrompt;
    private final NotificationManager notificationManager;

    RateMyApp(Context context, OptionalValue<Integer> daysUntilPrompt, OptionalValue<Integer> launchesUntilPrompt, NotificationManager notificationManager){
        if(context == null){
            throw new IllegalArgumentException("context should not be null");
        }
        if(( daysUntilPrompt.hasValue() && daysUntilPrompt.value() < 0 )  || (launchesUntilPrompt.hasValue() && launchesUntilPrompt.value() < 0))
        {
            throw new IllegalArgumentException("Expected non-negative values");
        }
        this.context = context;
        this.notificationManager = notificationManager;
        // If notificationManager is null the class is going to call itself
        this.daysUntilPrompt = daysUntilPrompt;
        this.launchesUntilPrompt = launchesUntilPrompt;
        this.preferencesManager = PreferencesManager.buildFromContext(context);
    }

    public void appLaunched(){
        if(preferencesManager.alertEnabled()){
            if(canShowDialog()){
                if(notificationManager == null){
                    showDialog();
                }
                else{
                    notificationManager.showDialog();
                }
            }
            else{
                preferencesManager.incrementLaunchCounter();
            }
        }
    }

    private boolean launchCounterConditionsMet(){
        if(launchesUntilPrompt.hasValue()){
            return preferencesManager.launchCounter() >= launchesUntilPrompt.value();
        }
        else{
            return true;
        }
    }

    private boolean daysElapsedConditionsMet(){
        if(daysUntilPrompt.hasValue()){
            return preferencesManager.daysFromFirstLaunch() >= daysUntilPrompt.value();
        }
        else{
            return true;
        }
    }

    private boolean canShowDialog() {
        return launchCounterConditionsMet() && daysElapsedConditionsMet();
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
