package finalproject.csci205.com.ymca.view.module.tenminhack;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.view.MainActivity;

/**
 * Created by Malachi on 12/2/2016.
 */
public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        sendNotification("Good Job! Keep up the good work!");
    }

    private void sendNotification(String msg) {
        Log.i("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(
                this)
                .setContentTitle("10 minutes are up!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)
                .setContentIntent(contentIntent);

        alarmNotificationManager.notify(1, alarmNotificationBuilder.build());
        Log.i("AlarmService", "Notification sent.");
    }
}