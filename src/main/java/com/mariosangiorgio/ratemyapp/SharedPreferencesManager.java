package com.mariosangiorgio.ratemyapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager implements PreferencesManager{
    private final static String SHARED_PREFERENCES_ID = "RateMyApp";
    private final static String ALERT_ENABLED = "alertEnabled";
    private final static String LAUNCH_COUNTER = "launchCounter";
    private final static String FIRST_LAUNCH_TIMESTAMP = "firstLaunchTimestamp";
    private final static int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

    private final SharedPreferences sharedPreferences;

    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_ID, 0);
        if (!sharedPreferences.contains(FIRST_LAUNCH_TIMESTAMP)) {
            resetFirstLaunchTimestamp();
        }
    }

    public static SharedPreferencesManager buildFromContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context should not be null");
        }
        return new SharedPreferencesManager(context);
    }

    public boolean alertEnabled() {
        return sharedPreferences.getBoolean(ALERT_ENABLED, true);
    }

    public int launchCounter() {
        return sharedPreferences.getInt(LAUNCH_COUNTER, 0);
    }

    public long firstLaunchTimestamp() {
        return sharedPreferences.getLong(FIRST_LAUNCH_TIMESTAMP, 0);
    }

    public void incrementLaunchCounter() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LAUNCH_COUNTER, launchCounter() + 1);
        editor.commit();
    }

    public int daysFromFirstLaunch() {
        return (int) (System.currentTimeMillis() - firstLaunchTimestamp()) / MILLIS_IN_DAY;
    }

    public void setAlertEnabled(boolean enable) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ALERT_ENABLED, enable);
        editor.commit();
    }

    public void resetFirstLaunchTimestamp() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(FIRST_LAUNCH_TIMESTAMP, System.currentTimeMillis());
        editor.commit();
    }

    @Override
    public void resetLaunchCount() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LAUNCH_COUNTER, 0);
        editor.commit();
    }
}
