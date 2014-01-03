RateMyApp
=========

An Android module to kindly ask users to rate your application.

![alt tag](https://raw.github.com/mariosangiorgio/RateMyApp/master/media/screenshot.png)

You can embed RateMyApp in your application in the following way:

 * include the RateMyApp module. In Android Studio you simply need to go in the main project directory and type `git clone https://github.com/mariosangiorgio/RateMyApp.git`
 * add it to the included modules list in `settings.gradle`, which should look like this `include ':RateMyApp',':FutsalCoach'`
 * add it to the dependency in your main module `build.gradle` by adding `compile project(':RateMyApp')`
 * add the following code at the end of your main activity `onCreate` method:

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
 
