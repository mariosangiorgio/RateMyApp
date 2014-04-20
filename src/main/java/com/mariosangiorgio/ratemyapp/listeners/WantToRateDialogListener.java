package com.mariosangiorgio.ratemyapp.listeners;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;

import com.mariosangiorgio.ratemyapp.PreferencesManager;
import com.mariosangiorgio.ratemyapp.actions.Action;

public class WantToRateDialogListener implements DialogClickListener{
    private final Action action;
    private final PreferencesManager preferencesManager;

    public WantToRateDialogListener(PreferencesManager preferencesManager, Action action){
        if(preferencesManager == null){
            throw new IllegalArgumentException();
        }
        this.preferencesManager = preferencesManager;
        if(action == null){
            throw new IllegalArgumentException();
        }
        this.action = action;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int buttonPressed, Context context, FragmentManager fragmentManager) {
        switch(buttonPressed){
            case DialogInterface.BUTTON_POSITIVE:
                action.execute(context, fragmentManager);
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                preferencesManager.resetFirstLaunchTimestamp();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                preferencesManager.setAlertEnabled(false);
                break;
        }
        dialogInterface.dismiss();
    }
}
