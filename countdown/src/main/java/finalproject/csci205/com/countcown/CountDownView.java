package finalproject.csci205.com.countcown;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/******************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2016
 * <p>
 * Name: YMCA
 * Date: Nov 1, 2016
 * Time: 7:50:26 PM
 * <p>
 * Project: csci205_final
 * Package: finalproject.csci205.com.countcown
 * File: CountDownView
 * Description:
 * Homebrew CountDownView that defines a generic count down timer.
 * ****************************************
 */

/**
 * @author Charles
 */
public class CountDownView extends LinearLayout implements View.OnClickListener, CountDownListener {

    private final int SECONDSPARAM = 1000;
    private int startPauseCounter = 0;
    private long storedTime;
    private TextView mins;
    private TextView seconds;
    private ImageButton cancelPom;
    private LinearLayout timerContainer;
    private CountDownTimer cdStart = null;
    private CountDownListener countDownListener;
    private CountDownService cd;


    public CountDownView(Context context) {
        super(context);
        init();
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    /**
     * Creates view elements
     *
     * @author Charles
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void init() {
        View root = inflate(getContext(), R.layout.countdownlayout, this);
        mins = (TextView) root.findViewById(R.id.mins);
        seconds = (TextView) root.findViewById(R.id.seconds);
        cancelPom = (ImageButton) root.findViewById(R.id.cancelBtn);
        cancelPom.setVisibility(GONE);
        timerContainer = (LinearLayout) root.findViewById(R.id.layoutContainer);
        timerContainer.setOnClickListener(this);
        cancelPom.setOnClickListener(this);

        if (isMyServiceRunning(CountDownService.class)) {
            Toast.makeText(getContext(), "Running", Toast.LENGTH_SHORT).show();
            cd = getContext().getSystemService(CountDownService.class);
            cd.setCountDownListener(this);
        } else {
            //Intent i = new Intent(this,CountDownService.class);
            //cd = getContext().getSystemService(CountDownService.class);
            cd = new CountDownService("test", 30);
            cd.setCountDownListener(this);
            getContext().getApplicationContext().startService(new Intent(getContext(), CountDownService.class));
        }
    }


    /**
     * Updates view to reflect accurate process
     *
     * @param l -  time
     * @author Charles
     */
    public void updateProgress(long l) {
        Date date = new Date(l);
        DateFormat minFor = new SimpleDateFormat("mm");
        DateFormat secFor = new SimpleDateFormat("ss");
        mins.setText(minFor.format(date));
        seconds.setText(secFor.format(date));
    }


    @Override
    public void onClick(View view) {
        if (isMyServiceRunning(CountDownService.class)) {
            Toast.makeText(getContext(), "Running", Toast.LENGTH_SHORT).show();
        }

        if (view.getId() == timerContainer.getId()) {
            cancelPom.setVisibility(VISIBLE);
            cd.startPauseCounter();
        } else if (view.getId() == cancelPom.getId()) {

            cancelPom.setVisibility(GONE);
            cd.stopTimer();
            mins.setText("30");//TODO Dont have this hard coded.
            seconds.setText("00");

        }
    }

    @Override
    public void countdownResult(long l) {
        updateProgress(l);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



    /*
        TODO
        Make accessers to change the state of the view for breaks.
     */


}
