package finalproject.csci205.com.countdown;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Homebrew CountDownView that defines a generic count down timer. Uses custom built
 * count down service
 * ****************************************
 */

/**
 * @author Charles
 */
public class CountDownView extends LinearLayout implements View.OnClickListener, ServiceConnection, CountDownListener {


    private final int SECONDSPARAM = 1000;
    private final int REBINDSERVICE = 0;
    private int sessionTime;
    private int startPauseCounter = 0;
    private View root;
    private TextView mins;
    private TextView seconds;
    private ImageButton cancelPom;
    private LinearLayout timerContainer;
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
    public void init() {
        root = inflate(getContext(), R.layout.countdownlayout, this);
        mins = (TextView) root.findViewById(R.id.mins);
        seconds = (TextView) root.findViewById(R.id.seconds);
        cancelPom = (ImageButton) root.findViewById(R.id.cancelBtn);
        cancelPom.setVisibility(GONE);
        timerContainer = (LinearLayout) root.findViewById(R.id.layoutContainer);
        timerContainer.setOnClickListener(this);
        cancelPom.setOnClickListener(this);

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


    /**
     * Note, implementing onClick in this view takes presidence over any other View that
     * creates this object. It is INTENDED to be a create and forget utility
     *
     * @param view
     * @author Charles
     */
    @Override
    public void onClick(View view) {


        if (cd != null) {
            if (view.getId() == timerContainer.getId()) {

                if (startPauseCounter == 0) { //Start
                    Log.i("click", "Start");
                    cancelPom.setVisibility(VISIBLE);
                    cd.startTimer();
                } else if (startPauseCounter % 2 != 0) { //Click to pause
                    Log.i("click", "Pause");
                    cd.pauseTimer();
                } else { //Click to resume
                    Log.i("click", "Resume");
                    cd.resume();
                }
                startPauseCounter++;


            } else if (view.getId() == cancelPom.getId()) {
                Log.i("click", "CANCEL/END");
                cancelPom.setVisibility(GONE);
                cd.stopTimer();
                mins.setText("30");//TODO Dont have this hard coded.
                seconds.setText("00");
                startPauseCounter = 0;
            }
        } else {

        }
    }


    /**
     * Sets the session time from data model, then inits the data.
     * Cannot create a service without the known session time!
     * @param sessionTime
     */
    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
        Log.d("SERVICE", "Service starting logic");
        CountDownIntent i = new CountDownIntent(getContext(), sessionTime);
        if (isMyServiceRunning(CountDownService.class)) {
            Log.d("SER", "service alive and well");
            getContext().bindService(i, this, REBINDSERVICE);

        } else {
            Log.d("SER", "service no no bro, starting new one");
            getContext().bindService(i, this, Context.BIND_AUTO_CREATE);

        }

    }


    /**
     * Gets the binder upon successfull connection, creates our local refrence to our service
     * Specifc to the view, sets the counter to a proper value depending on state.
     * Sets Callback listener for value updates.
     *
     * @param componentName
     * @param iBinder
     * @author Charles
     */
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        CountDownService.CountDownBinder binder = (CountDownService.CountDownBinder) iBinder;
        cd = binder.getService();
        cd.setCountDownListener(this);

        switch (cd.getState()) {
            case ISRUNNING:
                startPauseCounter = 1;
                configState();
                break;
            case PAUSED:
                startPauseCounter = 2;
                configState();
                break;


        }

    }

    private void configState() {
        cancelPom.setVisibility(VISIBLE);
        updateProgress(cd.getStoredTime());
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }


    /**
     * Checks to see if service passed is running
     *
     * @param serviceClass
     * @return
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Call Back from service.
     * @author
     * @param l
     */
    @Override
    public void countdownResult(long l) {
        updateProgress(l);
    }

    /*
        TODO
        Make accessers to change the state of the view for breaks.
     */


}
