package com.mariosangiorgio.ratemyapp;

import android.content.Context;
import android.text.TextUtils;

import com.mariosangiorgio.ratemyapp.actions.Action;
import com.mariosangiorgio.ratemyapp.actions.BuildTwoPhaseDialog;
import com.mariosangiorgio.ratemyapp.listeners.RatingRequestListener;

public class RateMyAppBuilder {
    private int launchesBeforeAlert = -1;
    private int daysBeforeAlert = -1;
    private NotificationManager notificationManager = null;
    private String emailAddress = null;

    public RateMyAppBuilder setNotificationManager(NotificationManager notificationManager){
        if(notificationManager == null){
            throw new IllegalArgumentException("Not-null NotificationManager expected");
        }
        this.notificationManager = notificationManager;
        return this;
    }

    public RateMyAppBuilder setLaunchesBeforeAlert(int launchesBeforeAlert){
        if(launchesBeforeAlert < 0){
            throw new IllegalArgumentException("Expected a non-negative number");
        }
        this.launchesBeforeAlert = launchesBeforeAlert;
        return this;

    }

    public RateMyAppBuilder setDaysBeforeAlert(int daysBeforeAlert){
        if(daysBeforeAlert < 0){
            throw new IllegalArgumentException("Expected a non-negative number");
        }
        this.daysBeforeAlert = daysBeforeAlert;
        return this;
    }

    private boolean isValid(String emailAddress){
        return !TextUtils.isEmpty(emailAddress) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    public RateMyAppBuilder setEmailAddress(String emailAddress){
        if(!isValid(emailAddress)){
            throw new IllegalArgumentException("Invalid email address "+emailAddress);
        }
        this.emailAddress = emailAddress;
        return this;
    }
    
    public RateMyApp build(Context context, PreferencesManager preferencesManager){
        OptionalValue<Integer> daysBeforeAlert = this.daysBeforeAlert == -1 ?
                new OptionalValue<Integer>() : new OptionalValue<Integer>(this.daysBeforeAlert);
        OptionalValue<Integer> launchesBeforeAlert = this.launchesBeforeAlert == -1 ?
                new OptionalValue<Integer>() : new OptionalValue<Integer>(this.launchesBeforeAlert);
        if(emailAddress == null){
            return new RateMyApp(context, preferencesManager, daysBeforeAlert, launchesBeforeAlert, notificationManager);
        }
        else{
            Action twoPhaseAction = new BuildTwoPhaseDialog(context, emailAddress);
            RatingRequestListener listener = new RatingRequestListener(twoPhaseAction, context);
            return new RateMyApp(context, preferencesManager, daysBeforeAlert, launchesBeforeAlert, notificationManager, listener);
        }
    }
}
