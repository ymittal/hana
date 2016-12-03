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

    //POMODORO
    /**
     * Default Pomodoro session period in minutes
     */
    public static final int DEFAULT_SESSION_TIME_IN_MINS = 25;
    /**
     * Default Pomodoro normal break period in minutes
     */
    public static final int DEFAULT_NORMAL_BREAK_IN_MINS = 5;
    /**
     * Default number of Pomodoro cycles
     */
    public static final int DEFAULT_NUM_CYCLES = 5;
    /**
     * Default Pomodoro long break period in minutes
     */
    public static final int DEFAULT_LONG_BREAK_IN_MINS = 15;



    public static void destroyPomNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }
//    public enum NotificationState{
//        START = ("START"),
//        PAUSE = ("PAUSE"),
//        CANCEL = ("CANCEL");
//
//        @Override
//        public String toString() {
//            return super.toString();
//        }
//    }
}
