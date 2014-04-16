package com.mariosangiorgio.ratemyapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.text.TextUtils;

import com.mariosangiorgio.ratemyapp.actions.Action;
import com.mariosangiorgio.ratemyapp.actions.ShowDialogAction;
import com.mariosangiorgio.ratemyapp.actions.OpenPlayStoreAction;
import com.mariosangiorgio.ratemyapp.dialogs.AbstractDialogFragment;
import com.mariosangiorgio.ratemyapp.dialogs.AbstractDialogFragmentFactory;
import com.mariosangiorgio.ratemyapp.dialogs.NumberOfStarsDialog;
import com.mariosangiorgio.ratemyapp.dialogs.SendEmailDialog;
import com.mariosangiorgio.ratemyapp.dialogs.WantToRateDialog;
import com.mariosangiorgio.ratemyapp.listeners.NumberOfStarsDialogListener;
import com.mariosangiorgio.ratemyapp.listeners.SentEmailDialogListener;
import com.mariosangiorgio.ratemyapp.listeners.WantToRateDialogListener;

public class RateMyAppBuilder {
    private int launchesBeforeAlert = -1;
    private int daysBeforeAlert = -1;
    private Action notificationAction = null;
    private String emailAddress = null;

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
    
    public RateMyApp build(Activity context, PreferencesManager preferencesManager){
        FragmentManager fragmentManager = context.getFragmentManager();
        OptionalValue<Integer> daysBeforeAlert = this.daysBeforeAlert == -1 ?
                new OptionalValue<Integer>() : new OptionalValue<Integer>(this.daysBeforeAlert);
        OptionalValue<Integer> launchesBeforeAlert = this.launchesBeforeAlert == -1 ?
                new OptionalValue<Integer>() : new OptionalValue<Integer>(this.launchesBeforeAlert);

        if(notificationAction == null){
            Action playStoreAction = new OpenPlayStoreAction(context,preferencesManager);

            Action innerAction;
            if(emailAddress != null){
                Action negativeAction = new ShowDialogAction(
                        fragmentManager,
                        new AbstractDialogFragmentFactory() {
                            @Override
                            public AbstractDialogFragment BuildInstance() {
                                return new SendEmailDialog();
                            }
                        },
                        new SentEmailDialogListener(context, preferencesManager, emailAddress),
                        "SendEmailAction"
                );
                innerAction = new ShowDialogAction(
                        fragmentManager,
                        new AbstractDialogFragmentFactory() {
                            @Override
                            public AbstractDialogFragment BuildInstance() {
                                return new NumberOfStarsDialog();
                            }
                        },
                        new NumberOfStarsDialogListener(playStoreAction, negativeAction),
                        "NumberOfStarsDialog"
                );
            }
            else{
                innerAction = playStoreAction;
            }
            notificationAction = new ShowDialogAction(
                    fragmentManager,
                    new AbstractDialogFragmentFactory() {
                        @Override
                        public AbstractDialogFragment BuildInstance() {
                            return new WantToRateDialog();
                        }
                    },
                    new WantToRateDialogListener(preferencesManager, innerAction),
                    "WantToRateDialog"
            );
        }
        return new RateMyApp(preferencesManager, daysBeforeAlert, launchesBeforeAlert, notificationAction);
    }
}
