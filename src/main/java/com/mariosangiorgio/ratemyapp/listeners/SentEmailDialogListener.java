package com.mariosangiorgio.ratemyapp.listeners;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.mariosangiorgio.ratemyapp.PreferencesManager;
import com.mariosangiorgio.ratemyapp.R;

public class SentEmailDialogListener implements DialogClickListener{
    private final PreferencesManager preferencesManager;
    private final String emailAddress;
    private final String emailMessage;

    public SentEmailDialogListener(PreferencesManager preferencesManager, String emailAddress, String emailMessage) {
        if (preferencesManager == null) {
            throw new IllegalArgumentException("preferencesManager should not be null");
        }
        this.preferencesManager = preferencesManager;
        if (emailAddress == null) {
            throw new IllegalArgumentException("emailAddress should not be null");
        }
        this.emailAddress = emailAddress;
        this.emailMessage = emailMessage != null ? emailMessage : "";
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int buttonPressed, Context context, FragmentManager fragmentManager) {
        switch(buttonPressed){
            case DialogInterface.BUTTON_POSITIVE:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddress, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject));
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage);
                context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.send_email)));
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                preferencesManager.resetFirstLaunchTimestamp();
                break;
        }
        dialogInterface.dismiss();
    }
}
