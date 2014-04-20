package com.mariosangiorgio.ratemyapp.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.mariosangiorgio.ratemyapp.listeners.DialogClickListener;

public abstract class AbstractDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{
    private DialogClickListener actualListener;

    @Override
    abstract public Dialog onCreateDialog(Bundle savedInstanceState);

    public void setActualListener(DialogClickListener actualListener){
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
    public void onClick(DialogInterface dialog, int buttonPressed) {
        if(actualListener != null && getActivity() != null && getFragmentManager() != null){
            actualListener.onClick(dialog, buttonPressed, getActivity(), getFragmentManager());
        }
        else{
            Log.e("RateMyApp", "No actual listener registered for "+dialog);
        }
    }
}
