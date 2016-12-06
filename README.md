# Hana

Hana is an research-based productivity application built for Android. This project was developed for CSCI 205: Software Engineering & Design course during Fall 2016 at Bucknell University.

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

### Requirements

* Android Studio, preferably 2.2.+
* Android SDK Build Tools v25.0.0
* Emulator or Android Kitkat 4.4 or above 

### Installation

Following are the two ways you can use our application.
1. Install using `.apk` file in `dist/` folder. You would need to enable _Install from Unknown Sources_ in the Security Settings on your Android.
2. Import the entire project in [Android Studio](https://developer.android.com/studio/index.html) and download all SDK requirements.
    * Install project dependencies through `sync gradle`
    * Run `app` module on an emulator or an Android

    **Note:** Please disable Instant Run feature on Android Studio before running the app. [`SugarORM`](https://github.com/satyan/sugar) has a [history](https://github.com/satyan/sugar/issues/75) of not creating tables when Instant Run is on.

## Authors

This project is brought to you by the following people:
* Charles Hennessey <charles.hennessey@gmail.com>
* Malachi Musick <malachimusick@gmail.com>
* Aleksandar Antonov <alekzandar@live.com>
* Yash Mittal <yashmittal2009@bucknell.edu>