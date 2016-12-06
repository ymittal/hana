package finalproject.csci205.com.ymca.view.module.tenminhack;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Class to handle Alarm receive event
 *
 * @author Malachi
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    /**
     * Length of vibration in milliseconds
     */
    public static final int VIBRATION_LENGTH_IN_MILLIS = 1000;

    /**
     * The method called when the alarm is scheduled to go off
     * <p>
     * Currently when alarm is triggered, a notification popped up saying the user completed
     * their 10 minutes, however this is not currently implemented. So far 10-minute hack is
     * only a user-set alarm.
     *
     * @param context The passed context from when the alarm was created
     * @param intent  The passed intent from when the alarm was created
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        //Do a little ring and vibrate for the notification
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATION_LENGTH_IN_MILLIS);

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}