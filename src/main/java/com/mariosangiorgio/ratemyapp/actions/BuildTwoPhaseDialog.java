package com.mariosangiorgio.ratemyapp.actions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.mariosangiorgio.ratemyapp.ContextUtils;
import com.mariosangiorgio.ratemyapp.R;

public class BuildTwoPhaseDialog extends Action{
    private final String emailAddress;

    public BuildTwoPhaseDialog(Context context, String emailAddress){
        super(context);
        this.emailAddress = emailAddress;
    }

    @Override
    public void execute(){
        AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.rate) + " " + ContextUtils.getApplicationName(context));
        DialogListenerStarNumber listenerStarNumber = new DialogListenerStarNumber();
        dialogBuilder.setPositiveButton(R.string.value_store, listenerStarNumber);
        dialogBuilder.setNegativeButton(R.string.value_email, listenerStarNumber);
        dialogBuilder.setMessage(R.string.rate_score);
    }

    private class DialogListenerStarNumber implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int buttonPressed) {
            switch(buttonPressed){
                case DialogInterface.BUTTON_POSITIVE:
                    (new OpenPlayStoreAction(context)).execute();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    DialogListenerSendEmail listenerEmail = new DialogListenerSendEmail();
                    AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(context);
                    dialogBuilder.setTitle(context.getString(R.string.rate)+" "+ContextUtils.getApplicationName(context));
                    dialogBuilder.setMessage(context.getString(R.string.email_feedback));
                    dialogBuilder.setPositiveButton(context.getString(R.string.send_email), listenerEmail);
                    dialogBuilder.setNegativeButton(context.getString(R.string.not_now), listenerEmail);
                    dialogBuilder.show();
                    break;
            }
            dialogInterface.dismiss();
        }

        private class DialogListenerSendEmail implements DialogInterface.OnClickListener{

            @Override
            public void onClick(DialogInterface dialogInterface, int buttonPressed) {
                switch(buttonPressed){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", emailAddress, null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.send_email)));
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        preferencesManager.resetFirstLaunchTimestamp();
                        break;
                }
                dialogInterface.dismiss();
            }
        }
    }
}
