package com.mariosangiorgio.ratemyapp.listeners;

import android.content.Context;
import android.content.DialogInterface;

import com.mariosangiorgio.ratemyapp.PreferencesManager;
import com.mariosangiorgio.ratemyapp.actions.Action;

public class RatingRequestListener implements DialogInterface.OnClickListener{
    private Action action;
    private PreferencesManager preferencesManager;

    public RatingRequestListener(Action action, Context context){
        if(action == null || context == null){
            throw new IllegalArgumentException();
        }
        this.action = action;
        this.preferencesManager = PreferencesManager.buildFromContext(context);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int buttonPressed) {
        switch(buttonPressed){
            case DialogInterface.BUTTON_POSITIVE:
                action.execute();
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
