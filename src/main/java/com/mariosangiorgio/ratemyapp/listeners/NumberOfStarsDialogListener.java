package com.mariosangiorgio.ratemyapp.listeners;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;

import com.mariosangiorgio.ratemyapp.actions.Action;

public class NumberOfStarsDialogListener implements DialogClickListener{
    private final Action positiveAction;
    private final Action negativeAction;

    public NumberOfStarsDialogListener(Action positiveAction, Action negativeAction) {
        if (positiveAction == null) {
            throw new IllegalArgumentException("positiveAction should not be null");
        }
        this.positiveAction = positiveAction;

        if (negativeAction == null) {
            throw new IllegalArgumentException("negativeAction should not be null");
        }
        this.negativeAction = negativeAction;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int buttonPressed, Context context, FragmentManager fragmentManager) {
        switch (buttonPressed) {
            case DialogInterface.BUTTON_POSITIVE:
                positiveAction.execute(context, fragmentManager);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                negativeAction.execute(context, fragmentManager);
        }
        dialogInterface.dismiss();
    }
}
