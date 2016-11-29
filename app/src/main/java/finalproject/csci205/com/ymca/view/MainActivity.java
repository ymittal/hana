package finalproject.csci205.com.ymca.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.util.SharedPreferenceUtil;

/**
 * {@link android.app.Activity} to hold the splash screen, manages logic for
 * first time events
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Amount of time in milliseconds before the splash screen
     * calls the next screen
     */
    public final static int DELAY_MILLIS = 1500;
    /**
     * Duration of animation in milliseconds
     */
    public final static int ANIMATION_DURATION_MILLIS = 4000;
    /**
     * Number of optional backgrounds in the application
     */
    private final static int NUM_BACKGROUNDS = 3;
    // User Interface components of MainActivity
    private TextView tvBeginButton;
    private TextView tvQuote;

    /**
     * Sets up activity user interface and controls
     *
     * @param savedInstanceState
     * @author Aleks and Malachi
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // makes the status bar transparent
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initUI();

        // checks if user has already opened the app
        if (!SharedPreferenceUtil.getIsOpen(getApplicationContext())) {
            beginAnimationOnFirstLaunch();

        } else {
            tvQuote.setVisibility(View.INVISIBLE);
            tvBeginButton.setVisibility(View.INVISIBLE);

            // delays proceeding to NavActivity by DELAY_MILLIS milliseconds
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    goToNavActivity();
                }
            }, DELAY_MILLIS);

        }
    }

    /**
     * Initializes User Interface elements and loads splash screen background
     *
     * @author Malachi and Aleks
     */
    private void initUI() {
        tvQuote = (TextView) findViewById(R.id.tvQuote);
        tvBeginButton = (TextView) findViewById(R.id.tvBeginButton);

        tvBeginButton.setOnClickListener(this);

        loadBackground();
    }

    /**
     * Starts animation on {@link #tvBeginButton} on every instance of
     * {@link MainActivity} launch until user opens the main app for the
     * first time
     *
     * @author Malachi
     */
    private void beginAnimationOnFirstLaunch() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation.setDuration(ANIMATION_DURATION_MILLIS);

                Animation translationAnimation = new TranslateAnimation(0.0f, 0.0f, -100.0f, 0.0f);
                translationAnimation.setDuration(ANIMATION_DURATION_MILLIS);

                AnimationSet animationSet = new AnimationSet(false);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(translationAnimation);

                tvBeginButton.startAnimation(animationSet);
                tvBeginButton.setAlpha(1.0f);
            }
        }, DELAY_MILLIS);
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
        int r = new Random().nextInt(NUM_BACKGROUNDS);
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

    /**
     * Handles click events on views implementing {@link android.view.View.OnClickListener}
     *
     * @param view clicked view
     * @author Malachi
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvBeginButton) {
            SharedPreferenceUtil.setPreferenceIsOpen(getApplicationContext(), true);
            goToNavActivity();
        }
    }
}
