# Hana!

Hana is an research-based productivity application built for Android. This app was developed for CSCI 205: Software Engineering & Design course during Fall 2016 at Bucknell University.

This application came to life when our team realized that there was a need for a Pomodoro timer which freely offered greater levels of customization. When browsing the market, we found several applications falling short due to a poor User Interface or lack of extensibility. When we came together, we decided to extend our application to serve a broader purpose.

Productivity hackers often employ a series of applications to ensure maximum value of their time. We found it absurd that many groups suggested the use of 3 or more apps which offered similar functionality. To service the broader needs of the market, we wanted to create an modular application that can implement many popular techniques. Our end product **Hana!** fulfills these needs by implementing Getting Things Done, Pomodoro, and the 10-Minute Hack techniques. 

## Files

```
app/
+-- src/
    +-- androidTest/
    +-- main/
    +-- test/
countdown/
design/
dist/
docs/
scrum/
```

### Description

* `app/` contains the source code (`src/main/`) along with the tests
* `countcown/` is a library created to implement a custom countdown
* `design/` contains CRC cards, Use-case diagram, and UML for the project
* `dist/` contains the generated `.apk` file
* `docs/` contains the user manual, the design manual, and the presentation presented in class on December 5, 2016
* `scrum/` contains user stories, backlogs, and task board

## Getting Started

### Prerequisites

* Android Studio, preferably 2.2.+
* Android SDK Build Tools v25.0.0
* Emulator or Android Kitkat 4.4 or above 

### Installation

Following are the two ways you can use our application.

1. Install using `.apk` file in `dist/` folder. You would need to enable _Install from Unknown Sources_ in the Security Settings on your Android.
2. Import the entire project in [Android Studio](https://developer.android.com/studio/index.html) and download all SDK requirements.
    * Install project dependencies from __Tools > Android > Sync Gradle with Project Files__
    * Run `app` module on an emulator or an Android

    **Note:** Please disable Instant Run feature on Android Studio before running the app. [`SugarORM`](https://github.com/satyan/sugar) has a [history](https://github.com/satyan/sugar/issues/75) of not creating tables when Instant Run is on.

## Testing

We utilized [`Espresso`](https://developer.android.com/training/testing/ui-testing/espresso-testing.html) library to test the UI functionality of our application. In Android, these tests are called Instrumentation Tests, and they can be found in `app/src/androidTest`. You can run the Instrumentation Tests as follows:
* Using the left pane, change the Project view to __Project__ (default)
* Open `app/src/androidTest/`
* Right-click on `androidTest/java/` and click __Run 'All Tests'__
* Select your preferred device to run UI tests on

In addition, the `JUnit` tests can be found in `app/src/test`. You do not need a device to run these tests.

## Authors

This project is brought to you by the following people:
* **Charles Hennessey** <charles.hennessey@gmail.com>
* **Malachi Musick** <malachimusick@gmail.com>
* **Aleksandar Antonov** <alekzandar@live.com>
* [**Yash Mittal**](https://github.com/ymittal) <yashmittal2009@bucknell.edu>
