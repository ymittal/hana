package finalproject.csci205.com.countdown.View;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import finalproject.csci205.com.countdown.R;
import finalproject.csci205.com.countdown.Service.CountDownIntent;
import finalproject.csci205.com.countdown.Service.CountDownListener;
import finalproject.csci205.com.countdown.Service.CountDownService;
import finalproject.csci205.com.countdown.Ults.Constants;
import finalproject.csci205.com.countdown.Ults.ServiceState;

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
public class CountDownView extends LinearLayout implements View.OnClickListener, ServiceConnection, CountDownListener, NotificationClickedSyncListener {

    private static CountDownView cdView;
    private final int REBINDSERVICE = 0;
    //Notification
    private final int NOTIFICATION_ID = 1;
    private int sessionTime;
    private int startPauseCounter = 0;
    private View root;
    private TextView mins;
    private TextView seconds;
    private ImageButton cancelPom;
    private LinearLayout timerContainer;
    private CountDownService cd;
    private RemoteViews notificationView;
    private NotificationManager notificationManager;
    private Notification notification;


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
        cdView = this;
        //Notification
//        CountDownNotification notification =
//                new CountDownNotification(getContext(), R.drawable.ic_pom
//                        , sessionTime, this);//
        //notificationProgress = (TextView) findViewById(R.id.ticker);


        startNotification();
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

        notificationView.setTextViewText(R.id.ticker, minFor.format(date) + " : " + secFor.format(date));
        notificationManager.notify(Constants.NOTIFICATION_ID, notification);
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

                resumeOrPause();


            } else if (view.getId() == cancelPom.getId()) {
                Log.i("click", "CANCEL/END");
                cancelPom.setVisibility(GONE);
                countCancelComplete();
            }
        } else {

        }
    }

    /**
     * Resumes or pauses service depending on num of clicks
     *
     * @author Charles
     */
    private void resumeOrPause() {
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
    }

    /**
     * Handles view if the counter was manually canceled by the user, or ran about its
     * time on its own. This method handles the functionality that is shared between both
     * events.
     *
     * @author Charles
     */
    private void countCancelComplete() {
        cd.stopTimer();
        Date date = new Date((long) sessionTime * 60000);
        DateFormat minFor = new SimpleDateFormat("mm");
        mins.setText(minFor.format(date));
        seconds.setText("00");
        startPauseCounter = 0;
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


        if (cd.getState() == ServiceState.ISRUNNING) {
            startPauseCounter = 1;
        } else if (cd.getState() == ServiceState.PAUSED) {
            startPauseCounter = 2;
        }
        configState();
    }


    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    private void configState() {
        cancelPom.setVisibility(VISIBLE);
        updateProgress(cd.getStoredTime());
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
     * @author Charles
     * @param l
     */
    @Override
    public void countdownResult(long l) {
        updateProgress(l);
    }

    /**
     * Call back from Service notifying that the count down is complete
     * Reset View.
     * @author Charles
     */
    @Override
    public void onCountFinished() {
        countCancelComplete();
    }


    @Override
    public void onStartClicked() {
        resumeOrPause();
    }

    @Override
    public void onPausedClicked() {
        resumeOrPause();
    }

    @Override
    public void onStopClicked() {
        countCancelComplete();
    }

    private void startNotification() {
        notificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        notification = new Notification(R.drawable.ic_pom, null,
                Constants.NOTIFICATION_ID_CONSTANT);


        notificationView = new RemoteViews(getContext().getPackageName(),
                R.layout.cd_notification_layout);

        //the intent that is started when the notification is clicked (works)
//        Intent notificationIntent = new Intent(context, CountDownService.class);
//        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 0,
//                notificationIntent, 0);

        notification.contentView = notificationView;
        //notification.contentIntent = pendingNotificationIntent;
        notification.flags |= Notification.FLAG_NO_CLEAR;


        /* Create intent, set context and destionation */
        Intent start = new Intent(getContext(), NotificationButtonListener.class);
        /* Put extra to identify which action was called. */
        start.putExtra(Constants.START, Constants.START);
        PendingIntent pendingStart = PendingIntent.getBroadcast(getContext(), 0,
                start, 0);

        /* Create intent, set context and destionation */
        Intent pauseIntent = new Intent(getContext(), NotificationButtonListener.class);
        /* Put extra to identify which action was called. */
        pauseIntent.putExtra(Constants.PAUSE, Constants.PAUSE);
        PendingIntent pendingPause = PendingIntent.getBroadcast(getContext(), 1,
                pauseIntent, 0);


        /* Create intent, set context and destionation */
        Intent cancelIntent = new Intent(getContext(), NotificationButtonListener.class);
        /* Put extra to identify which action was called. */
        cancelIntent.putExtra(Constants.CANCEL, Constants.CANCEL);
        PendingIntent pendingCancel = PendingIntent.getBroadcast(getContext(), 2,
                cancelIntent, 0);

        /* Functionally equal to setOnClickListener */
        notificationView.setOnClickPendingIntent(R.id.start, pendingStart);
        notificationView.setOnClickPendingIntent(R.id.pause, pendingPause);
        notificationView.setOnClickPendingIntent(R.id.cancel, pendingCancel);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }


    /**
     * Broadcast Receiver that intercepts Notification Button Clicks
     *
     * @author Charles
     */
    public static class NotificationButtonListener extends BroadcastReceiver {
        private final int NOTIFICATION_ID = 1;
//
//        public NotificationButtonListener(){
//            super();
//        }

        /**
         * Determines which button was clicked, then calls the correct listener on
         * CountDownView to interpert the action. This ensures that both the View
         * and notification are synced.
         *
         * @param context
         * @param intent
         * @author Charles
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("onReceive", "Test");
            NotificationClickedSyncListener clickedSyncListener = cdView;
            String startPotential = intent.getStringExtra(Constants.START);
            String pausePotential = intent.getStringExtra(Constants.PAUSE);
            String cancelPotential = intent.getStringExtra(Constants.CANCEL);
            if (startPotential != null) {
                clickedSyncListener.onStartClicked();
            } else if (pausePotential != null) {
                clickedSyncListener.onPausedClicked();
            } else if (cancelPotential != null) {
                clickedSyncListener.onStopClicked();
                NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(NOTIFICATION_ID);
                //TODO Get it to come back when user resumes timer
            }
        }


    }


}
