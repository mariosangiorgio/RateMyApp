RateMyApp
=========

An Android module to kindly ask users to rate your application.

[![Build Status](https://travis-ci.org/mariosangiorgio/RateMyApp.png?branch=master)](https://travis-ci.org/mariosangiorgio/RateMyApp)

![alt tag](https://raw.github.com/mariosangiorgio/RateMyApp/master/media/screenshot.png)

You can embed RateMyApp in your application either by downloading and including the library or (better) by declearing it as a dependency.

        dependencies {
            compile 'com.mariosangiorgio.RateMyApp:1.2.2'
        }

Version 1.2.2 contains the fix to a activity leakage on device orientation change.
I tried to test it as much as I have been able but I'd appreciate bug reports if something does not work as expected.

On your main activity you will need to override in the following wat the `onCreate` and `onSaveInstanceState` methods;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*
         * Do here other things you need to do on creation
         */

        RateMyAppBuilder builder = new RateMyAppBuilder();
        builder.setLaunchesBeforeAlert(3);  // Optional
        builder.setDaysBeforeAlert(7);      // Optional
        builder.setEmailAddress("mariosangiorgio@gmail.com");   // Optional. It will enable two-phase rating request
        PreferencesManager preferencesManager = SharedPreferencesManager.buildFromContext(this);

        RateMyApp rateMyApp = builder.build(this, preferencesManager);
        if(savedInstanceState == null || !savedInstanceState.getBoolean("rateMyAppLaunched", false)) {
            rateMyApp.appLaunched();
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        /*
         * Store here whatever you need to put in the bundle
         */

        bundle.putBoolean("rateMyAppLaunched", true);

        super.onSaveInstanceState(bundle);
    }

RateMyApp currently supports the following languages:

 * English (default)
 * Italian
 * German (thanks to bigjan)
 * Slovak (thanks to pylerSM)
 * Traditional Chinese (Taiwan) (thanks to Aerotinge)
 * Portuguese (thanks to RaphaDroid)

If you want to customize and of the message strings you can do it easily with [Android default resource override](http://stackoverflow.com/questions/4263259/replace-or-override-string-in-android-library-project)

Please note that if you want to compile the library by yourself you need to provide values for the `signing.password` and for `sonatypePassword` variables. You can either add them (with fake values, the real ones are needed only if you want to publish the library to maven central) to `gradle.properties` or pass them from the command line (by adding `-Psigning.password=SECRET -PsonatypePassword=SECRET` to the gradle invocation).

* Mimics the behavior described in this article: http://www.theverge.com/2014/2/9/5395338/ea-dungeon-keeper-review-scam-google-play-store

Basically, asks the user before he goes to Play Store if they will rate the app with 1-3 stars or 4-5.

1-3: ask them to send an email with feedback and DON'T TAKE THEM TO PLAY STORE
4-5: send them to Play Store normally
