package com.mariosangiorgio.ratemyapp.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class AbstractDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{
    private DialogInterface.OnClickListener actualListener;


    public void setActualListener(DialogInterface.OnClickListener actualListener){
        this.actualListener = actualListener;
    }

    @Override
    public void onCreate(Bundle icicle) {
        this.setCancelable(true);
        setRetainInstance(true);
        super.onCreate(icicle);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(actualListener != null){
            actualListener.onClick(dialog, which);
        }
        else{
            Log.e("RateMyApp", "No actual listener registered for "+dialog);
        }
    }
}
