package com.mariosangiorgio.ratemyapp.listeners;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;

public interface DialogClickListener {
    void onClick(DialogInterface dialogInterface, int buttonPressed, Context context, FragmentManager fragmentManager);
}
