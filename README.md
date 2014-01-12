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
