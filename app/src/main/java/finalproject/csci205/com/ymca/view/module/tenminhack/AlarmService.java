package finalproject.csci205.com.ymca.view.module.tenminhack;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.view.MainActivity;

/**
 * The class that manages what to do when the alarm has been sent. Should be sent from AlarmReceiver
 *
 * @author Malachi
 */
public class AlarmService extends IntentService {

    /**
     * Random and unique 10 digit id for the notification the TenMinuteFragment is responsible for
     */
    public static final int NOTIFICATION_ID = 19283792;

    /**
     * Required Constructor
     */
    public AlarmService() {
        super("AlarmService");
    }

    /**
     * The method called when the notification is to be created
     *
     * @param intent The passed intent from the creation of the notification
     * @author Malachi
     */
    @Override
    public void onHandleIntent(Intent intent) {
        sendNotification(getString(R.string.tenmin_completion_title), getString(R.string.tenmin_completion_content));
    }

    /**
     * Send a notification to the notification bar
     *
     * @param title   The title of the notification to be displayed
     * @param content The body of the notification to be displayed
     * @author Malachi
     */
    private void sendNotification(String title, String content) {
        NotificationManager alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        //Intent to pull up the app when the alarm goes off
        PendingIntent pendingIntent = PendingIntent.getActivity(this, TenMinuteFragment.REQUEST_CODE,
                new Intent(this, MainActivity.class), TenMinuteFragment.NO_FLAG);

        //Build the Notification
        NotificationCompat.Builder alarmNotificationBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.ic_tenmin)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                        .setContentText(content)
                        .setContentIntent(pendingIntent);

        //Send the notification
        alarmNotificationManager.notify(NOTIFICATION_ID, alarmNotificationBuilder.build());
    }
}