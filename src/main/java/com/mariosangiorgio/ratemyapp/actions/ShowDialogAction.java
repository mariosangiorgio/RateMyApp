package com.mariosangiorgio.ratemyapp.actions;

import android.app.FragmentManager;
import android.content.Context;

import com.mariosangiorgio.ratemyapp.dialogs.AbstractDialogFragment;
import com.mariosangiorgio.ratemyapp.listeners.DialogClickListener;

public class ShowDialogAction implements Action{
    private final String tag;
    private final Class<? extends AbstractDialogFragment> fragmentDialogClass;
    private final DialogClickListener actualListener;

    public ShowDialogAction(DialogClickListener actualListener,
                            Class<? extends AbstractDialogFragment> fragmentDialogClass,
                            String tag){

        if(actualListener == null){
            throw new IllegalArgumentException("actualListener can not be null");
        }
        this.actualListener = actualListener;

        if(fragmentDialogClass == null) {
            throw new IllegalArgumentException("FragmentDialog class can not be null");
        }
        this.fragmentDialogClass = fragmentDialogClass;

        if(tag == null){
            throw new IllegalArgumentException("fragment tag cannot be null.");
        }
        this.tag = tag;
    }

    @Override
    public void execute(Context context, FragmentManager fragmentManager){
        try {
            AbstractDialogFragment dialogFragment = fragmentDialogClass.newInstance();
            dialogFragment.setActualListener(actualListener);
            dialogFragment.show(fragmentManager, tag);
        } catch (Exception e) { }
    }
}
