package com.mariosangiorgio.ratemyapp.actions;

import android.app.FragmentManager;
import android.content.Context;

import com.mariosangiorgio.ratemyapp.PreferencesManager;
import com.mariosangiorgio.ratemyapp.dialogs.AbstractDialogFragment;
import com.mariosangiorgio.ratemyapp.dialogs.NumberOfStarsDialog;
import com.mariosangiorgio.ratemyapp.dialogs.SendEmailDialog;
import com.mariosangiorgio.ratemyapp.dialogs.WantToRateDialog;
import com.mariosangiorgio.ratemyapp.listeners.DialogClickListener;
import com.mariosangiorgio.ratemyapp.listeners.NumberOfStarsDialogListener;
import com.mariosangiorgio.ratemyapp.listeners.SentEmailDialogListener;
import com.mariosangiorgio.ratemyapp.listeners.WantToRateDialogListener;

public class ShowDialogAction implements Action {
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

    public final static class ShowDialogActionFactory {
        private ShowDialogActionFactory() { }

        public static ShowDialogAction getWantToRateAction(PreferencesManager preferencesManager) {
            return new ShowDialogAction(
                    new WantToRateDialogListener(preferencesManager, new OpenPlayStoreAction(preferencesManager))
                    , WantToRateDialog.class
                    , WantToRateDialog.class.getSimpleName());
        }

        public static ShowDialogAction getWantToRateAction(PreferencesManager preferencesManager, String emailAddress) {
            Action negativeEmailAction = new ShowDialogAction(
                    new SentEmailDialogListener(preferencesManager, emailAddress),
                    SendEmailDialog.class,
                    SendEmailDialog.class.getSimpleName()
            );

            Action numberOfStarsAction = new ShowDialogAction(
                    new NumberOfStarsDialogListener(new OpenPlayStoreAction(preferencesManager), negativeEmailAction),
                    NumberOfStarsDialog.class,
                    NumberOfStarsDialog.class.getSimpleName()
            );

            return new ShowDialogAction(
                    new WantToRateDialogListener(preferencesManager, numberOfStarsAction)
                    , WantToRateDialog.class
                    , WantToRateDialog.class.getSimpleName());
        }
    }
}
