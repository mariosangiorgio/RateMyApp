RateMyApp
=========

An Android module to kindly ask users to rate your application.

You can embed RateMyApp in your application in the following way:

 * include the RateMyApp module. In Android Studio you simply need to go in the main project directory and type `git clone https://github.com/mariosangiorgio/RateMyApp.git`
 * add it to the included modules list in `settings.gradle`, which should look like this `include ':RateMyApp',':FutsalCoach'`
 * add it to the dependency in your main module `build.gradle` by adding `compile project(':RateMyApp')`
 * add the following code at the end of your main activity `onCreate` method:

        int numberOfLaunchesUntilRequest = 3;
        int numberOfDaysUntilRequest = 7;
        RateMyApp rateMyApp = new RateMyApp( this, numberOfLaunchesUntilRequest , numberOfDaysUntilRequest );
        rateMyApp.appLaunched();

RateMyApp currently supports the following languages:

 * English (default)
 * Italian
