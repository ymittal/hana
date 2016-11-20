package finalproject.csci205.com.ymca.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Random;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.view.util.SharedPreferenceUtil;

/**
 * Class that holds the splash screen. Manages the logic for first time events
 * initial quote as well as starting of the tutorial)
 */
public class MainActivity extends AppCompatActivity {
    private final int DELAY_MILLIS = 1000;
    /**
     * The number of optional backgrounds in the application
     */
    private final int numOfBackgrounds = 3;

    /**
     * Method within the activity lifecycle that is called on the creation of the activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        loadBackground();

        //Login button that shows up on the bottom of the screen only the first time the user uses the app
        Button tempLogin = (Button) findViewById(R.id.tempLogin);
        //if first time...

        if (!SharedPreferenceUtil.getIsOpen(getApplicationContext())) {
            tempLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferenceUtil.setPreferenceIsOpen(getApplicationContext(), true);
                    goToNavActivity();
                }
            });
            //else don't show button
        } else {
            tempLogin.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    goToNavActivity();
                }
            }, DELAY_MILLIS);

        }

    }

    /**
     * Handles the logic needed to go to the NavActivity
     */
    private void goToNavActivity() {
        Intent i = new Intent(MainActivity.this, NavActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * Loads the background of the splash screen
     *
     * @author Malachi Musick
     */
    private void loadBackground() {
        RelativeLayout thisRL = (RelativeLayout) findViewById(R.id.relativeLayout);

        //Generate a random number between 0 and the numOfBackgrounds available, and randomly select the splash background.
        //Needs to be more dynamic
        int r = new Random().nextInt(numOfBackgrounds);
        switch (r) {
            case 0:
                thisRL.setBackgroundResource(R.drawable.splash_cliff);
                break;
            case 1:
                thisRL.setBackgroundResource(R.drawable.splash_notebook);
                break;
            case 2:
                thisRL.setBackgroundResource(R.drawable.splash_map);
                break;
        }
    }
}
