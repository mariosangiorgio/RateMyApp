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
        if(notificationManager == null){
            this.notificationManager = this;
        }
        else{
            this.notificationManager = notificationManager;
        }
        this.daysUntilPrompt = daysUntilPrompt;
        this.launchesUntilPrompt = launchesUntilPrompt;
        this.preferencesManager = PreferencesManager.buildFromContext(context);
    }

    public void appLaunched(){
        if(preferencesManager.alertEnabled()){
            if(canShowDialog()){
                notificationManager.showDialog(1);
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

    public void showDialog(int flowPosition) {

        AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.rate) + " " + getApplicationName(context));



        switch(flowPosition){
            case 1:
                DialogListener listener = new DialogListener();
                dialogBuilder.setNeutralButton(R.string.later_button, listener);
                dialogBuilder.setPositiveButton(R.string.rate_button, listener);
                dialogBuilder.setNegativeButton(R.string.never_button, listener);
                dialogBuilder.setMessage(R.string.rate_message);
                break;
            case 2:
                DialogListenerStarNumber listenerStarNumber = new DialogListenerStarNumber();
                dialogBuilder.setPositiveButton(R.string.value_store, listenerStarNumber);
                dialogBuilder.setNegativeButton(R.string.value_email, listenerStarNumber);
                dialogBuilder.setMessage(R.string.rate_score);
                break;
        }


        dialogBuilder.show();
    }

    private class DialogListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int buttonPressed) {
            switch(buttonPressed){
                case DialogInterface.BUTTON_POSITIVE:


                    showDialog(2);
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

    private class DialogListenerStarNumber implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int buttonPressed) {
            switch(buttonPressed){
                case DialogInterface.BUTTON_POSITIVE:
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id" + context.getPackageName())));
                    preferencesManager.disableAlert();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    DialogListenerSendEmail listenerEmail = new DialogListenerSendEmail();
                    AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(context);
                    dialogBuilder.setTitle(context.getString(R.string.rate)+" "+getApplicationName(context));
                    dialogBuilder.setMessage(context.getString(R.string.email_feedback));
                    dialogBuilder.setPositiveButton(context.getString(R.string.send_email), listenerEmail);
                    dialogBuilder.setNegativeButton(context.getString(R.string.not_now), listenerEmail);
                    dialogBuilder.show();
                    break;
            }
            dialogInterface.dismiss();
        }

        private class DialogListenerSendEmail implements DialogInterface.OnClickListener{

            @Override
            public void onClick(DialogInterface dialogInterface, int buttonPressed) {
                switch(buttonPressed){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "YOUR_EMAIL", null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.send_email)));
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        preferencesManager.resetFirstLaunchTimestamp();
                        break;
                }
                dialogInterface.dismiss();
            }
        }
    }
}
