package com.mariosangiorgio.ratemyapp;

import android.content.Context;
import android.text.TextUtils;

import com.mariosangiorgio.ratemyapp.actions.Action;
import com.mariosangiorgio.ratemyapp.actions.ShowDialogAction.ShowDialogActionFactory;

public class RateMyAppBuilder {
    private int launchesBeforeAlert = -1;
    private int daysBeforeAlert = -1;
    private Action notificationAction = null;
    private String emailAddress = null;
    private String emailMessage = null;

    public RateMyAppBuilder setNotificationAction(Action notificationAction){
        if(notificationAction == null){
            throw new IllegalArgumentException("Not-null notificationAction expected");
        }
        this.notificationAction = notificationAction;
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

    public RateMyAppBuilder setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
        return this;
    }

    public RateMyApp build(Context context) {
        PreferencesManager preferencesManager = SharedPreferencesManager.buildFromContext(context);
        return build(preferencesManager);
    }
    
    public RateMyApp build(PreferencesManager preferencesManager){
        OptionalValue<Integer> daysBeforeAlert = this.daysBeforeAlert == -1 ?
                new OptionalValue<Integer>() : new OptionalValue<Integer>(this.daysBeforeAlert);
        OptionalValue<Integer> launchesBeforeAlert = this.launchesBeforeAlert == -1 ?
                new OptionalValue<Integer>() : new OptionalValue<Integer>(this.launchesBeforeAlert);

        if(notificationAction == null){

            if (TextUtils.isEmpty(emailAddress)) {
                notificationAction = ShowDialogActionFactory.getWantToRateAction(preferencesManager);
            } else {
                notificationAction = ShowDialogActionFactory.getWantToRateAction(preferencesManager, emailAddress, emailMessage);
            }
        }
        return new RateMyApp(preferencesManager, daysBeforeAlert, launchesBeforeAlert, notificationAction);
    }
}
