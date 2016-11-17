package finalproject.csci205.com.ymca.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        Button tempLogin = (Button) findViewById(R.id.tempLogin);

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
            }, 1500);

        }

    }

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
        //current implementation is static. TODO: Dynamically choose theBackground

        RelativeLayout thisRL = (RelativeLayout) findViewById(R.id.relativeLayout);

        Drawable theBackground;

        //randomly pick a background
        int r = new Random().nextInt(4);

        // thisRL.setBackground(theBackground);
    }
}
