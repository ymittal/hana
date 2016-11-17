package finalproject.csci205.com.ymca.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Random;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        loadBackground();

        if (SharedPreferenceUtil.getIsOpen(getApplicationContext())){
            Log.d("LOG_TAG", "true");
        }
        else {
            Log.d("LOG_TAG", "false");
        }
        /*
            Check HERE if Shared Prefrence / Sugar data exists

            if (data isn't here) {

            Button tempLogin = (Button) findViewById(R.id.tempLogin);
            tempLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NavActivity.class);
                startActivity(i);
                finish();
            } else {

                Intent i = new Intent(MainActivity.this, NavActivity.class);
                startActivity(i);
                finish();

            }
        });

            }
         */

        //Note, to keep the close clean, refactor the else block into a method.
        //Here's some useful data to get you started

        /*
            > https://www.tutorialspoint.com/android/android_shared_preferences.htm
            > https://developer.android.com/training/basics/data-storage/shared-preferences.html
            > https://developer.android.com/reference/android/content/SharedPreferences.html
         */


        Button tempLogin = (Button) findViewById(R.id.tempLogin);
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
        //current implementation is static. TODO: Dynamically choose theBackground

        RelativeLayout thisRL = (RelativeLayout) findViewById(R.id.relativeLayout);



        //randomly pick a background
        int r = new Random().nextInt(4);

        switch (r){
            case 0: thisRL.setBackgroundResource(R.drawable.cliff);
            break;
            case 1: thisRL.setBackgroundResource(R.drawable.open_notebook);
            break;
            case 2: thisRL.setBackgroundResource(R.drawable.skyscraper);
            break;
            case 3: thisRL.setBackgroundResource(R.drawable.splash_map);
        }



    }
}
