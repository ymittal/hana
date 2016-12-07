package finalproject.csci205.com.countdown.Service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import finalproject.csci205.com.countdown.Ult.Constants;
import finalproject.csci205.com.countdown.Ult.ServiceState;

/**
 *
 * Custom service that implments a count down timer with start, pause, stop, and cancel
 * functionality.
 *
 * @author Charles
 *         <p>
 *         Refrences
 *         > https://guides.codepath.com/android/Creating-Custom-Listeners
 *         > https://github.com/commonsguy/cw-andtutorials/tree/master/18-LocalService
 *         > https://developer.android.com/guide/components/services.html#Stopping
 */

public class CountDownService extends Service {

    /**
     * Seconds parameter constant, see android documentation
     */
    private final int SECONDSPARAM = 1000;
    /**
     * Connection between this, and whatever connects
     */
    private final IBinder returnBinder = new CountDownBinder();
    /**
     * Local time storage
     */
    private long storedTime;
    private int sessionTime; // In mins
    /**
     * Abstract timer
     */
    private CountDownTimer cdStart = null;
    /**
     * Internal state, set to other by default
     */
    private ServiceState state = ServiceState.OTHER;
    /**
     * Listener that informs operation's status
     */
    private CountDownListener countDownListener;



    public CountDownService() {

    }

    /**
     * IntentService / LifeCycle Methods
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //1 is passed by default
        this.sessionTime = intent.getIntExtra(Constants.STRINGEXTRA, 1);
        return returnBinder;
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
                countDownListener.onCountFinished();
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
                    storedTime = l;
                }

                @Override
                public void onFinish() {
                    countDownListener.onCountFinished();
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

    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
    }

    /**
     * Stops timer, sends out Finish Msg.
     * @author Charles
     */
    public void stopTimer() {
        if (cdStart != null) {
            cdStart.cancel();
            state = ServiceState.OTHER;
        }
    }


    public long getStoredTime() {
        return storedTime;
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

    public void resetStoredTime() {
        storedTime = sessionTime * 60000; //miliseconds conv.
    }

    public ServiceState getState() {
        return state;
    }



    /**
     * Connection between client and service
     *
     * @author Charles
     */
    public class CountDownBinder extends Binder {
        public CountDownService getService() {
            return CountDownService.this;
        }
    }


}
