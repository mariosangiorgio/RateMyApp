package com.mariosangiorgio.ratemyapp;

import android.app.AlertDialog;
import android.content.Context;

import com.mariosangiorgio.ratemyapp.actions.OpenPlayStoreAction;
import com.mariosangiorgio.ratemyapp.listeners.RatingRequestListener;

public class RateMyApp implements  NotificationManager{
    private final Context context;
    private final PreferencesManager preferencesManager;
    private final OptionalValue<Integer> daysUntilPrompt;
    private final OptionalValue<Integer> launchesUntilPrompt;
    private final NotificationManager notificationManager;
    private final RatingRequestListener listener;

    RateMyApp(
            Context context,
            PreferencesManager preferencesManager,
            OptionalValue<Integer> daysUntilPrompt,
            OptionalValue<Integer> launchesUntilPrompt,
            NotificationManager notificationManager,
            RatingRequestListener listener
    ){
        if(context == null){
            throw new IllegalArgumentException("context should not be null");
        }
        if(listener == null){
            throw new IllegalArgumentException("listener should not be null");
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
        if(preferencesManager == null){
            throw new IllegalArgumentException("preferences manager should not be null");
        }
        this.preferencesManager = preferencesManager;
        this.listener = listener;
    }

    RateMyApp(Context context, PreferencesManager preferencesManager, OptionalValue<Integer> daysUntilPrompt, OptionalValue<Integer> launchesUntilPrompt, NotificationManager notificationManager){
        this(context, preferencesManager, daysUntilPrompt, launchesUntilPrompt, notificationManager, new RatingRequestListener(new OpenPlayStoreAction(context), context));
    }

    public void appLaunched(){
        if(preferencesManager.alertEnabled()){
            if(canShowDialog()){
                notificationManager.showDialog();
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

    public void showDialog() {

        AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.rate) + " " + ContextUtils.getApplicationName(context));

        dialogBuilder.setNeutralButton(R.string.later_button, listener);
        dialogBuilder.setPositiveButton(R.string.rate_button, listener);
        dialogBuilder.setNegativeButton(R.string.never_button, listener);
        dialogBuilder.setMessage(R.string.rate_message);

        dialogBuilder.show();
    }
}
