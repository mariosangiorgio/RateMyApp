package com.mariosangiorgio.ratemyapp;

public interface PreferencesManager {
    boolean alertEnabled();

    void incrementLaunchCounter();

    int launchCounter();

    int daysFromFirstLaunch();

    void setAlertEnabled(boolean enable);

    void resetFirstLaunchTimestamp();

    void resetLaunchCount();
}
