package com.mariosangiorgio.ratemyapp.actions;

import android.content.Context;

import com.mariosangiorgio.ratemyapp.PreferencesManager;

public abstract class Action {
    protected final Context context;
    protected final PreferencesManager preferencesManager;

    public Action(Context context){
        if(context == null){
            throw new IllegalArgumentException();
        }
        this.context = context;
        this.preferencesManager = PreferencesManager.buildFromContext(context);
    }

    public abstract void execute();
}
