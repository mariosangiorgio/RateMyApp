package com.mariosangiorgio.ratemyapp.listeners;

import android.content.DialogInterface;

import com.mariosangiorgio.ratemyapp.actions.Action;

public class NumberOfStarsDialogListener implements DialogInterface.OnClickListener{
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
    public void onClick(DialogInterface dialogInterface, int buttonPressed) {
        switch (buttonPressed) {
            case DialogInterface.BUTTON_POSITIVE:
                positiveAction.execute();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                negativeAction.execute();
                break;
        }
        dialogInterface.dismiss();
    }
}
