package com.mariosangiorgio.ratemyapp;

import android.util.Log;

import com.mariosangiorgio.ratemyapp.actions.Action;

public class RateMyApp {
    private final PreferencesManager preferencesManager;
    private final OptionalValue<Integer> daysUntilPrompt;
    private final OptionalValue<Integer> launchesUntilPrompt;
    private final Action action;

    RateMyApp(
            PreferencesManager preferencesManager,
            OptionalValue<Integer> daysUntilPrompt,
            OptionalValue<Integer> launchesUntilPrompt,
            Action action
    ){
        if(( daysUntilPrompt.hasValue() && daysUntilPrompt.value() < 0 )  || (launchesUntilPrompt.hasValue() && launchesUntilPrompt.value() < 0))
        {
            throw new IllegalArgumentException("Expected non-negative values");
        }
        this.daysUntilPrompt = daysUntilPrompt;
        this.launchesUntilPrompt = launchesUntilPrompt;
        if(preferencesManager == null){
            throw new IllegalArgumentException("preferences manager should not be null");
        }
        this.preferencesManager = preferencesManager;
        if(action == null){
            throw new IllegalArgumentException("action should not be null");
        }
        this.action = action;
    }

    public void appLaunched(){
        Log.i("RateMyApp", "Application launch registered");
        if(preferencesManager.alertEnabled()){
            if(canShowDialog()){
                action.execute();
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
}
