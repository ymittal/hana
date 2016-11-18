package finalproject.csci205.com.countdown;

/******************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2016
 * <p>
 * Name: YMCA
 * Date: Nov 1, 2016
 * Time: 7:50:26 PM
 * <p>
 * Project: csci205_final
 * Package: finalproject.csci205.com.countcown
 * File: CountDownView
 * Description:
 * Services that keeps track of counting down time.
 * ****************************************
 */


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author Charles
 *         <p>
 *         Refrences
 *         > https://guides.codepath.com/android/Creating-Custom-Listeners
 *         > https://github.com/commonsguy/cw-andtutorials/tree/master/18-LocalService
 */

public class CountDownService extends Service {

    private final int SECONDSPARAM = 1000;
    private final IBinder returnBinder = new CountDownBinder();
    private long storedTime;
    private int sessionTime = 30; // In mins
    private CountDownTimer cdStart = null;
    private ServiceState state = ServiceState.OTHER;
    private CountDownListener countDownListener;



    public CountDownService() {

    }

    /* IntentService / LifeCycle Methods */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.sessionTime = intent.getIntExtra(Constants.STRINGEXTRA, 1);

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return returnBinder;
    }

//    @Override
//    protected void onHandleIntent(Intent intent) {
//
//    }

    @Override
    public void onCreate() {
        super.onCreate();

    }




    /**
     * Starts the inital timer.
     * @author Charles
     */
    public void startTimer() {
        cdStart = new CountDownTimer(minToMili(sessionTime), SECONDSPARAM) {
            @Override
            public void onTick(long l) {
                countDownListener.countdownResult(l);
                Log.d("SERVICE", String.valueOf(l));
                storedTime = l;
            }

            @Override
            public void onFinish() {
            }

        };
        cdStart.start();
        state = ServiceState.ISRUNNING;
    }

    /**
     * Resume's timer
     *
     * @author Charles
     */
    public void resume() {
        if (storedTime != 0) {
            cdStart = new CountDownTimer(storedTime, SECONDSPARAM) {
                @Override
                public void onTick(long l) {
                    countDownListener.countdownResult(l);
                    Log.d("SERVICE", String.valueOf(l));
                    storedTime = l;
                }

                @Override
                public void onFinish() {

                }
            };
            cdStart.start();
            state = ServiceState.ISRUNNING;
        } else {

        }
    }


    /**
     * Pauses timer, logically, but really it cancels it
     * Assumes user properly follows this usage sequence
     *  1.) Start 2) Pause/Resume/Pause/Resume.....n 3) Stop 4.) Goto 1.
     * @author Charles
     */
    public void pauseTimer() {
        cdStart.cancel();
        state = ServiceState.PAUSED;

    }

    /**
     * Stops timer, sends out Finish Msg.
     * @author Charles
     */
    public void stopTimer() {
        cdStart.cancel();
        cdStart.onFinish();
        state = ServiceState.OTHER;
    }


    /**
     * Converts Minuetes to Miliseconds
     *
     * @param min
     * @return
     */
    public long minToMili(int min) {
        return min * 60000;
    }


    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }

    public ServiceState getState() {
        return state;
    }
    /**
     * Syncs current timer with a defined notification
     *
     * @author Charles
     */
    public void deployNotification() {

    }

    /**
     * Connection between client and service
     *
     * @author Charles
     */
    public class CountDownBinder extends Binder {
        CountDownService getService() {
            return CountDownService.this;
        }
    }

}
