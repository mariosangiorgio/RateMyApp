package com.mariosangiorgio.ratemyapp.actions;

import android.app.FragmentManager;
import android.content.DialogInterface;

import com.mariosangiorgio.ratemyapp.dialogs.AbstractDialogFragment;
import com.mariosangiorgio.ratemyapp.dialogs.AbstractDialogFragmentFactory;

public class ShowDialogAction implements Action{
    private final AbstractDialogFragmentFactory dialogFragmentFactory;
    private final FragmentManager fragmentManager;
    private final String tag;
    private final DialogInterface.OnClickListener actualListener;

    public ShowDialogAction(FragmentManager fragmentManager,
                            AbstractDialogFragmentFactory dialogFragmentFactory,
                            DialogInterface.OnClickListener actualListener,
                            String tag){
        if(fragmentManager == null){
            throw new IllegalArgumentException();
        }
        this.fragmentManager = fragmentManager;

        if(dialogFragmentFactory == null){
            throw new IllegalArgumentException();
        }
        this.dialogFragmentFactory = dialogFragmentFactory;

        if(actualListener == null){
            throw new IllegalArgumentException();
        }
        this.actualListener = actualListener;

        if(tag == null){
            throw new IllegalArgumentException();
        }
        this.tag = tag;
    }

    @Override
    public void execute(){
        AbstractDialogFragment dialogFragment = dialogFragmentFactory.BuildInstance();
        dialogFragment.setActualListener(actualListener);
        dialogFragment.show(fragmentManager, tag);
    }
}
