package com.mariosangiorgio.ratemyapp.actions;

import android.app.FragmentManager;
import android.content.Context;

public interface Action {
    void execute(Context context, FragmentManager fragmentManager);
}
