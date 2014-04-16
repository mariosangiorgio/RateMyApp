package com.mariosangiorgio.ratemyapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.mariosangiorgio.ratemyapp.ContextUtils;
import com.mariosangiorgio.ratemyapp.R;

public class SendEmailDialog extends AbstractDialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.rate) + " " + ContextUtils.getApplicationName(context));
        dialogBuilder.setMessage(context.getString(R.string.email_feedback));
        dialogBuilder.setPositiveButton(context.getString(R.string.send_email), this);
        dialogBuilder.setNegativeButton(context.getString(R.string.not_now), this);
        return dialogBuilder.create();
    }
}