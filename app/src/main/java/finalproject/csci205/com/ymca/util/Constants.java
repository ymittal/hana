package finalproject.csci205.com.ymca.util;

import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by ceh024 on 11/24/16.
 */

public class Constants {
    //NOTIFICATION BROADCAST
    public static final String START = "START";
    public static final String PAUSE = "PAUSE";
    public static final String CANCEL = "CANCEL";
    //NOTIFICATION
    public static final int NOTIFICATION_ID_CONSTANT = 123456789;
    public static final int NOTIFICATION_ID = 1;

    public static void destroyPomNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }
}
