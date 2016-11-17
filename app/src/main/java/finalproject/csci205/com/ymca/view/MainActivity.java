package finalproject.csci205.com.ymca.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.view.util.SharedPreferenceUtil;

public class MainActivity extends AppCompatActivity {

    //TODO Check if user has opened app before, if they have ship this screen and go to nav act
        /*
            Note for Alex: To check if a user has opened the application, you have two ways of going
            about this.

            First, you would create a shared prefrence in this class, and store it locally. The next
            time the user would start the application, the application will check in its onCreate() method
            if this prefrence is null. If it is not, then skip this activity, and load the NavActivity

            Method two is the same logic per-say, but instead of Shared Prefrence, you would store some
            dummy data using Sugar-ORM, and again check if that data exists or is null!
         */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button tempLogin = (Button) findViewById(R.id.tempLogin);


        //Check HERE if Shared Preference / Sugar data exists

        if (!SharedPreferenceUtil.getIsOpen(getApplicationContext())) { // has not been opened before

            Log.d("LOG_TAG", "false");


            tempLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferenceUtil.setPreference(getApplicationContext());
                    Intent i = new Intent(MainActivity.this, NavActivity.class);
                    startActivity(i);
                    finish();
                    System.out.println("LOG_TAG " + SharedPreferenceUtil.getIsOpen(getApplicationContext()));
                }
            });

        } else {                                            //If it has been opened before
            Log.d("LOG_TAG", "true");
            tempLogin.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent i = new Intent(MainActivity.this, NavActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 2000);

        }

    }
}

        //Note, to keep the close clean, refactor the else block into a method.
        //Here's some useful data to get you started

        /*
            > https://www.tutorialspoint.com/android/android_shared_preferences.htm
            > https://developer.android.com/training/basics/data-storage/shared-preferences.html
            > https://developer.android.com/reference/android/content/SharedPreferences.html
         */

<<<<<<< HEAD

        Button tempLogin = (Button) findViewById(R.id.tempLogin);
=======
/*
>>>>>>> 04565ffacca68058c9736bc2cfe45c43efb18818
        tempLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NavActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * Loads the background of the splash screen
     *
     * @author Malachi Musick
     */
    private void loadBackground() {

    }
}
*/
