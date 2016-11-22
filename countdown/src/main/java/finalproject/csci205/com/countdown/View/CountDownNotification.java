package finalproject.csci205.com.countdown.View;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import finalproject.csci205.com.countdown.R;
import finalproject.csci205.com.countdown.Service.CountDownIntent;
import finalproject.csci205.com.countdown.Service.CountDownService;
import finalproject.csci205.com.countdown.Ults.Constants;


/**
 * Created by ceh024 on 11/18/16.
 * Refs
 *  > http://www.vogella.com/tutorials/AndroidNotifications/article.html#notification-manager
 */

public class CountDownNotification implements ServiceConnection {


    public static CountDownService cd = null;
    public static CountDownView cdView;
    private final String TITLE = "Time remaining";
    private final int REFRENCE_ID = 1;
    private Context context;
    private int smallIcon;


    public CountDownNotification(Context context, int smallIcon, int sessionTime, CountDownView cdView) {
        this.context = context;
        this.smallIcon = smallIcon;
        CountDownNotification.cdView = cdView;
        CountDownIntent i = new CountDownIntent(context, sessionTime);
        context.bindService(i, this, 0);

    }


    private void startNotification() {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification(smallIcon, null,
                System.currentTimeMillis());


        RemoteViews notificationView = new RemoteViews(context.getPackageName(),
                R.layout.cd_notification_layout);

        //the intent that is started when the notification is clicked (works)
//        Intent notificationIntent = new Intent(context, CountDownService.class);
//        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 0,
//                notificationIntent, 0);

        notification.contentView = notificationView;
        //notification.contentIntent = pendingNotificationIntent;
        notification.flags |= Notification.FLAG_NO_CLEAR;


        /* Create intent, set context and destionation */
        Intent start = new Intent(context, NotificationButtonListener.class);
        /* Put extra to identify which action was called. */
        start.putExtra(Constants.START, Constants.START);
        PendingIntent pendingStart = PendingIntent.getBroadcast(context, 0,
                start, 0);

        /* Create intent, set context and destionation */
        Intent pauseIntent = new Intent(context, NotificationButtonListener.class);
        /* Put extra to identify which action was called. */
        pauseIntent.putExtra(Constants.PAUSE, Constants.PAUSE);
        PendingIntent pendingPause = PendingIntent.getBroadcast(context, 1,
                pauseIntent, 0);


        /* Create intent, set context and destionation */
        Intent cancelIntent = new Intent(context, NotificationButtonListener.class);
        /* Put extra to identify which action was called. */
        cancelIntent.putExtra(Constants.CANCEL, Constants.CANCEL);
        PendingIntent pendingCancel = PendingIntent.getBroadcast(context, 2,
                cancelIntent, 0);

        /* Functionally equal to setOnClickListener */
        notificationView.setOnClickPendingIntent(R.id.start, pendingStart);
        notificationView.setOnClickPendingIntent(R.id.pause, pendingPause);
        notificationView.setOnClickPendingIntent(R.id.cancel, pendingCancel);

        notificationManager.notify(1, notification);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        CountDownService.CountDownBinder binder = (CountDownService.CountDownBinder) iBinder;
        cd = binder.getService();
        //cd.setCountDownListener(this);
        startNotification();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }


    public static class NotificationButtonListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Broadcast", "Broadcast received");
            NotificationClickedSyncListener clickedSyncListener = cdView;
            String startPotential = intent.getStringExtra(Constants.START);
            String pausePotential = intent.getStringExtra(Constants.PAUSE);
            String cancelPotential = intent.getStringExtra(Constants.CANCEL);
            if (startPotential != null) {
                Log.d("Broadcast", startPotential);
                //startPotential = null;
                //cd.resume();
                clickedSyncListener.onStartClicked();

            } else if (pausePotential != null) {
                Log.d("Broadcast", pausePotential);
                //pausePotential = null;
                clickedSyncListener.onPausedClicked();
            } else if (cancelPotential != null) {
                Log.d("Broadcast", cancelPotential);
                //cancelPotential = null;
                clickedSyncListener.onStopClicked();
            }


        }


    }



}
