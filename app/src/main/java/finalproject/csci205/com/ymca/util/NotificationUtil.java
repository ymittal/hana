package finalproject.csci205.com.ymca.util;

import android.app.NotificationManager;
import android.content.Context;

/**
 * Utility class to hold constants and functions related to {@link android.app.Notification}
 * related functionality
 *
 * @author Charles
 */
public class NotificationUtil {
    //NOTIFICATION BROADCAST
    public static final String START = "START";
    public static final String PAUSE = "PAUSE";
    public static final String CANCEL = "CANCEL";

    //NOTIFICATION
    public static final int NOTIFICATION_ID_CONSTANT = 123456789;
    public static final int NOTIFICATION_ID = 1;

    /**
     * Destroys Pomodoro Notification
     *
     * @param context
     * @author Charles
     */
    public static void destroyPomNotification(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }


}
