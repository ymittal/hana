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
import finalproject.csci205.com.ymca.util.SharedPreferenceUtil;

/**
 * {@link android.app.Activity} to hold the splash screen, manages logic for
 * first time events
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Amount of time in milliseconds before the splash screen
     * calls the next screen
     */
    private final int DELAY_MILLIS = 1000;
    /**
     * Number of optional backgrounds in the application
     */
    private final int numOfBackgrounds = 3;

    /**
     * Sets up activity user interface and controls
     *
     * @param savedInstanceState
     * @author Aleks
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // makes the status bar transparent
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        loadBackground();

        Button tempLogin = (Button) findViewById(R.id.tempLogin);

        // checks if user has already opened the app
        if (!SharedPreferenceUtil.getIsOpen(getApplicationContext())) {
            tempLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferenceUtil.setPreferenceIsOpen(getApplicationContext(), true);
                    goToNavActivity();
                }
            });
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
     *
     * @author Aleks
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

        // randomly chooses a background image for the splash screen
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
            default:
                thisRL.setBackgroundResource(R.drawable.splash_notebook);
        }
    }
}
