FORKED VERSION BY VINICIUS ALEXANDRE

Mimics the behavior described at this article: http://www.theverge.com/2014/2/9/5395338/ea-dungeon-keeper-review-scam-google-play-store

Basically, asks the user before he goes to Play Store if they will rate the app with 1~3 stars or 4~5.

1~3: ask them to send an email with feedback and DON'T TAKE THEM TO PLAY STORE
4~5: send them to Play Store normally

RateMyApp
=========

An Android module to kindly ask users to rate your application.

[![Build Status](https://travis-ci.org/mariosangiorgio/RateMyApp.png?branch=master)](https://travis-ci.org/mariosangiorgio/RateMyApp)

![alt tag](https://raw.github.com/mariosangiorgio/RateMyApp/master/media/screenshot.png)

You can embed RateMyApp in your application either by downloading and including the library or (better) by declearing it as a dependency.

        dependencies {
            compile 'com.mariosangiorgio.RateMyApp:1.0'
        }
    
Then add the following code at the end of your main activity `onCreate` method:

        RateMyAppBuilder builder = new RateMyAppBuilder();
        builder.setLaunchesBeforeAlert(3); // Optional
        builder.setDaysBeforeAlert(7);     // Optional
        builder.setNotificationManager(notificationManager);
        //Optional if you want to show the alert in a custom way
        RateMyApp rateMyApp = builder.build(this);
        rateMyApp.appLaunched();

RateMyApp currently supports the following languages:

 * English (default)
 * Italian
 * German (thanks to bigjan)
 * Slovak (thanks to pylerSM)
 * Traditional Chinese (Taiwan) (thanks to Aerotinge)
 * Portuguese (thanks to RaphaDroid)

Please note that if you want to compile the library by yourself you need to provide values for the `signing.password` and for `sonatypePassword` variables. You can either add them (with fake values, the real ones are needed only if you want to publish the library to maven central) to `gradle.properties` or pass them from the command line (by adding `-Psigning.password=SECRET -PsonatypePassword=SECRET` to the gradle invocation).
