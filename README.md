# RateMyApp

An Android module to kindly ask users to rate your application.

[![Build Status](https://travis-ci.org/mariosangiorgio/RateMyApp.png?branch=master)](https://travis-ci.org/mariosangiorgio/RateMyApp)

![alt tag](https://raw.github.com/mariosangiorgio/RateMyApp/master/media/screenshot.png)

* Mimics the behavior described in this article: http://www.theverge.com/2014/2/9/5395338/ea-dungeon-keeper-review-scam-google-play-store

Basically, asks the user before he goes to Play Store if they will rate the app with 1-3 stars or 4-5.

1-3: ask them to send an email with feedback and DOESN'T TAKE THEM TO PLAY STORE
4-5: send them to Play Store normally

## Include in project.

You can embed RateMyApp in your application either by downloading and including the library or (better) by declearing it as a dependency.

        dependencies {
            compile 'com.mariosangiorgio.RateMyApp:1.4.0'
        }

## ChangeLog
### Version 1.4
- Adds ShowFragmentActionFactory. This can help implementers customize the first action
- Adds the ability to customize email subject and add an email message.
- Removes unnecessary appcompat dependency.

### Version 1.2.2-1.3
- Removed memory leak by removing hard reference to Activities. `appLaunched()` and `build()` do not hold references to your activity.

## Example
On your main activity you will need to override in the following wat the `onCreate` and `onSaveInstanceState` methods;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Do here other things you need to do on creation
         */

        if(savedInstanceState == null ) { // This null guard protects us from calling appLaunched on rotation.
            RateMyAppBuilder builder = new RateMyAppBuilder();
            builder.setLaunchesBeforeAlert(3);  // Optional
            builder.setDaysBeforeAlert(7);      // Optional
            builder.setEmailAddress("mariosangiorgio@gmail.com");   // Optional. It will enable two-phase rating request

            RateMyApp rateMyApp = builder.build(this);
            rateMyApp.appLaunched(this);
        }
    }

## Languages

RateMyApp currently supports the following languages:

 * English (default)
 * Italian
 * German (thanks to bigjan)
 * Slovak (thanks to pylerSM)
 * Traditional Chinese (Taiwan) (thanks to Aerotinge)
 * Portuguese (thanks to RaphaDroid)

## Customize Strings

If you want to customize any of the message strings you can do it easily with [Android default resource override](http://stackoverflow.com/questions/4263259/replace-or-override-string-in-android-library-project)

## Build Yourself

Please note that if you want to compile the library by yourself you need to provide values for the `signing.password` and for `sonatypePassword` variables. You can either add them (with fake values, the real ones are needed only if you want to publish the library to maven central) to `gradle.properties` or pass them from the command line (by adding `-Psigning.password=SECRET -PsonatypePassword=SECRET` to the gradle invocation).
