package finalproject.csci205.com.countdown.View;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import finalproject.csci205.com.countdown.R;
import finalproject.csci205.com.countdown.Ults.Constants;


/**
 * Created by ceh024 on 11/18/16.
 * Refs
 *  > http://www.vogella.com/tutorials/AndroidNotifications/article.html#notification-manager
 */

public class CountDownNotification {

    public static CountDownView cdView;
    private final int NOTIFICATION_ID = 1;
    private Context context;
    private int smallIcon;
    private RemoteViews notificationView;
    private NotificationManager notificationManager;
    private Notification notification;


    public CountDownNotification(Context context, int smallIcon, int sessionTime, CountDownView cdView) {
        this.context = context;
        this.smallIcon = smallIcon;
        CountDownNotification.cdView = cdView;

        startNotification();

    }

    private void updateNotification(String s) {
        notificationView.setTextViewText(R.id.ticker, s);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void startNotification() {
        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notification = new Notification(smallIcon, null,
                Constants.NOTIFICATION_ID_CONSTANT);


        notificationView = new RemoteViews(context.getPackageName(),
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

        notificationManager.notify(NOTIFICATION_ID, notification);
    }


    /**
     * Broadcast Receiver that intercepts Notification Button Clicks
     *
     * @author Charles
     */
    public static class NotificationButtonListener extends BroadcastReceiver {
        private final int NOTIFICATION_ID = 1;

        /**
         * Determines which button was clicked, then calls the correct listener on
         * CountDownView to interpert the action. This ensures that both the View
         * and notification are synced.
         * @author Charles
         * @param context
         * @param intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {

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
