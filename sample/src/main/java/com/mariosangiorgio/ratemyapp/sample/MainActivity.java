package com.mariosangiorgio.ratemyapp.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.mariosangiorgio.ratemyapp.RateMyApp;
import com.mariosangiorgio.ratemyapp.RateMyAppBuilder;
import com.mariosangiorgio.ratemyapp.SharedPreferencesManager;


public class MainActivity extends ActionBarActivity {
    static final String TAG = MainActivity.class.getSimpleName();

    private SharedPreferencesManager raterPrefs;
    private RateMyApp rater;

    // Widgets
    private TextWatcher textWatcher;
    private EditText editTextLaunches;
    private EditText editTextDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        raterPrefs = SharedPreferencesManager.buildFromContext(getApplicationContext());
        setupWidgets();
        initRater();
        if (savedInstanceState == null) {
            // This null guard protects us from calling appLaunched on rotation.
            rater.appLaunched();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        editTextLaunches.addTextChangedListener(getTextWatcher());
        editTextDays.addTextChangedListener(getTextWatcher());
    }

    @Override
    protected void onPause() {
        super.onPause();
        editTextDays.removeTextChangedListener(getTextWatcher());
        editTextLaunches.removeTextChangedListener(getTextWatcher());
    }

    ///////////////
    // Init's
    ///////////////

    private void setupWidgets() {
        setupEditTexts();
        setupTextViews();
        setupButtons();
    }

    private void setupEditTexts() {
        editTextLaunches = (EditText) findViewById(R.id.edittext_launch);
        editTextDays = (EditText) findViewById(R.id.edittext_days);
    }

    private void setupTextViews() {
        TextView textAlerts = (TextView) findViewById(R.id.textview_alerts);
        textAlerts.setText(getString(R.string.alerts_enabled, Boolean.toString(raterPrefs.alertEnabled())));

        TextView textLaunches = (TextView) findViewById(R.id.textview_launch);
        textLaunches.setText(getString(R.string.launch_count, raterPrefs.launchCounter()));

        TextView textDays = (TextView) findViewById(R.id.textview_days);
        textDays.setText(getString(R.string.days_count, raterPrefs.daysFromFirstLaunch()));
    }

    private void setupButtons() {
        findViewById(R.id.button_tigger_launch).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "App Launches = " + raterPrefs.launchCounter());
                rater.appLaunched(MainActivity.this);
                setupTextViews();
            }
        });

        findViewById(R.id.button_reset).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                raterPrefs.setAlertEnabled(true);
                raterPrefs.resetFirstLaunchTimestamp();
                raterPrefs.resetLaunchCount();
                setupTextViews();
            }
        });
    }

    private void initRater() {
        RateMyAppBuilder builder = new RateMyAppBuilder();
        builder.setLaunchesBeforeAlert(getNumberOfLaunches())
                .setDaysBeforeAlert(getNumberOfDays())
                .setEmailAddress("test@test.com");

        rater = builder.build(this);
    }

    ///////////////
    // Getters
    ///////////////

    private int getNumberOfLaunches() {
        return getIntFromEditText(editTextLaunches);
    }

    private int getNumberOfDays() {
        return getIntFromEditText(editTextDays);
    }

    private int getIntFromEditText(EditText editText) {
        String text = editText.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            return Integer.valueOf(text);
        }
        return 0;
    }

    private TextWatcher getTextWatcher() {
        if (textWatcher == null) {
            textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    initRater();
                }
            };
        }
        return textWatcher;
    }
}
