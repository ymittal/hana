package finalproject.csci205.com.countdown.View;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import finalproject.csci205.com.countdown.R;
import finalproject.csci205.com.countdown.Service.CountDownService;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by ceh024 on 11/18/16.
 * Refs
 *  > http://www.vogella.com/tutorials/AndroidNotifications/article.html#notification-manager
 */

public class CountDownNotification {


    private final String TITLE = "Time remaining";
    private final int REFRENCE_ID = 1;
    private Context context;
    private int smallIcon;
    private int actionIcon1;
    private int actionIcon2;
    private int actionIcon3;
    private android.support.v4.app.NotificationCompat.Builder builder;
    private PendingIntent cancel;
    private PendingIntent start;
    private PendingIntent pause;

    public CountDownNotification(Context context, int smallIcon, int actionIcon1, int actionIcon2, int actionIcon3) {
        this.context = context;
        this.smallIcon = smallIcon;
        this.actionIcon1 = actionIcon1;
        this.actionIcon2 = actionIcon2;
        this.actionIcon3 = actionIcon3;
        deployNotification();
        startNotification();
    }

    private void deployNotification() {
        // prepare intent which is triggered if the
        // notification is selected

        Intent intent = new Intent(context, CountDownService.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getService(context,
                (int) System.currentTimeMillis(), intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n = new Notification.Builder(context)
                .setContentTitle("Time Remaining")
                .setContentText("00 : 00")
                .setSmallIcon(smallIcon)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(actionIcon1, "Start", pIntent)
                .addAction(actionIcon2, "Pause", pIntent)
                .addAction(actionIcon3, "Cancel", pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

    private void startNotification() {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(ns);

        Notification notification = new Notification(smallIcon, null,
                System.currentTimeMillis());


        RemoteViews notificationView = new RemoteViews(context.getPackageName(),
                R.layout.test);

        //the intent that is started when the notification is clicked (works)
        Intent notificationIntent = new Intent(context, CountDownService.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        notification.contentView = notificationView;
        notification.contentIntent = pendingNotificationIntent;
        notification.flags |= Notification.FLAG_NO_CLEAR;


        //this is the intent that is supposed to be called when the
        //button is clicked
        Intent switchIntent = new Intent(context, switchButtonListener.class);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0,
                switchIntent, 0);

        notificationView.setOnClickPendingIntent(R.id.notificationCancel,
                pendingSwitchIntent);

        notificationManager.notify(1, notification);
    }


    public static class switchButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }



}
