package com.mariosangiorgio.ratemyapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.mariosangiorgio.ratemyapp.ContextUtils;
import com.mariosangiorgio.ratemyapp.R;

public class WantToRateDialog extends AbstractDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.rate) + " " + ContextUtils.getApplicationName(context));

        dialogBuilder.setNeutralButton(R.string.later_button, this);
        dialogBuilder.setPositiveButton(R.string.rate_button, this);
        dialogBuilder.setNegativeButton(R.string.never_button, this);
        dialogBuilder.setMessage(R.string.rate_message);
        return dialogBuilder.create();
    }
}
