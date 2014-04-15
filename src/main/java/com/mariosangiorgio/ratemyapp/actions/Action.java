package com.mariosangiorgio.ratemyapp.actions;

import android.content.Context;

import com.mariosangiorgio.ratemyapp.SharedPreferencesManager;

public abstract class Action {
    protected final Context context;
    protected final SharedPreferencesManager preferencesManager;

    public Action(Context context){
        if(context == null){
            throw new IllegalArgumentException();
        }
        this.context = context;
        this.preferencesManager = SharedPreferencesManager.buildFromContext(context);
    }

    public abstract void execute();
}
