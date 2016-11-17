package finalproject.csci205.com.countcown;

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
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * @author Charles
 *         <p>
 *         Refrences
 *         > https://guides.codepath.com/android/Creating-Custom-Listeners
 *         > https://github.com/commonsguy/cw-andtutorials/tree/master/18-LocalService
 */

public class CountDownService extends Service {
    private final int SECONDSPARAM = 1000;
    private int startPauseCounter = 0;
    private long storedTime;
    private int sessionTime; // In mins 
    private CountDownTimer cdStart = null;
    private CountDownListener countDownListener;

    public CountDownService() {
        this.sessionTime = 30;
    }

    public CountDownService(int sessionTime) {
        this.sessionTime = sessionTime;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
                storedTime = l;
            }

            @Override
            public void onFinish() {
                stopSelf();
            }

        };
        cdStart.start();
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
                    storedTime = l;
                }

                @Override
                public void onFinish() {
                    stopSelf();
                }
            };
            cdStart.start();
        } else {
            Toast.makeText(getApplicationContext(), "Unable to resume, restarting", Toast.LENGTH_SHORT).show();
            startTimer();
        }
    }

    /**
     * Pauses timer, logically, but really it cancels it
     *
     * @author Charles
     */
    public void pauseTimer() {
        cdStart.cancel();
    }

    /**
     * Stops timer, destroys task and the service.
     * @author Charles
     */
    public void stopTimer() {
        cdStart.cancel();
        cdStart.onFinish();
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

    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
    }

    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }



}
