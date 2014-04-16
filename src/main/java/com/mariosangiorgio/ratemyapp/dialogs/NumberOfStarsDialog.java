package com.mariosangiorgio.ratemyapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Context;
import android.os.Bundle;

import com.mariosangiorgio.ratemyapp.ContextUtils;
import com.mariosangiorgio.ratemyapp.R;
import com.mariosangiorgio.ratemyapp.actions.Action;

public class NumberOfStarsDialog extends AbstractDialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.rate) + " " + ContextUtils.getApplicationName(context));
        dialogBuilder.setPositiveButton(R.string.value_store, this);
        dialogBuilder.setNegativeButton(R.string.value_email, this);
        dialogBuilder.setMessage(R.string.rate_score);

        return dialogBuilder.create();
    }
}
