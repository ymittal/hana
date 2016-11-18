package finalproject.csci205.com.countdown.View;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import finalproject.csci205.com.countdown.R;


/**
 * Created by ceh024 on 11/18/16.
 */

public class CountDownNotification {


    private final String TITLE = "Time remaining";
    private final int REFRENCE_ID = 1;
    private Context context;
    private int icon;
    private NotificationCompat.Builder builder;
    private PendingIntent cancel;
    private PendingIntent start;
    private PendingIntent pause;

    public CountDownNotification(Context context, int icon) {
        this.context = context;
        this.icon = icon;
    }

    public void setIntent(PendingIntent p) {
        this.cancel = p;
    }

    public void buildNotfication() {
        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(TITLE)
                .setContentIntent(pause)
                .addAction(R.drawable.ic_cancel, "Cancel", cancel);
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(REFRENCE_ID, builder.build());
    }

}
