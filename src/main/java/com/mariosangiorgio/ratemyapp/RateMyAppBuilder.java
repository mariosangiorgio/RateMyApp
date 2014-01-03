package com.mariosangiorgio.ratemyapp;

import android.content.Context;

public class RateMyAppBuilder {
    private int launchesBeforeAlert = -1;
    private int daysBeforeAlert = -1;
    private NotificationManager notificationManager = null;

    public RateMyAppBuilder setNotificationManager(NotificationManager notificationManager){
        if(notificationManager == null){
            throw new NullPointerException("Not-null NotificationManager expected");
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

    public RateMyApp build(Context context){
        OptionalValue<Integer> daysBeforeAlert = this.daysBeforeAlert == -1 ?
                new OptionalValue<Integer>() : new OptionalValue<Integer>(this.daysBeforeAlert);
        OptionalValue<Integer> launchesBeforeAlert = this.launchesBeforeAlert == -1 ?
                new OptionalValue<Integer>() : new OptionalValue<Integer>(this.launchesBeforeAlert);
        return new RateMyApp(context, daysBeforeAlert, launchesBeforeAlert, notificationManager);
    }
}
