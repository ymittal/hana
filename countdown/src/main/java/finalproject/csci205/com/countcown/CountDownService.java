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


import android.app.IntentService;
import android.content.Intent;
import android.os.CountDownTimer;

/**
 * @author Charles
 */

public class CountDownService extends IntentService {
    private final int SECONDSPARAM = 1000;
    private int startPauseCounter = 0;
    private long storedTime;
    private int sessionTime; // In mins 
    private CountDownTimer cdStart = null;
    private CountDownListener countDownListener;
    //    private ViewList viewList;

    //lol dont use this.
    public CountDownService(String name) {
        super(name);
    }

    public CountDownService(String name, int sessionTime) {
        super(name);
        this.sessionTime = sessionTime;

    }


    @Override
    public void onCreate() {
        super.onCreate();
        //startPauseCounter();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    /**
     * Starts/Stops counter depending on the state of the counter
     *
     * @author Charles
     */
    public void startPauseCounter() {
        if (startPauseCounter == 0) {
            startPauseCounter++;
            cdStart = new CountDownTimer(minToMili(sessionTime), SECONDSPARAM) {
                @Override
                public void onTick(long l) {

                    countDownListener.countdownResult(l);
                    storedTime = l;
                }

                @Override
                public void onFinish() {

                }

            };
            cdStart.start();
        } else if (startPauseCounter % 2 != 0) {
            startPauseCounter++;
            cdStart.cancel();

        } else if (startPauseCounter % 2 == 0) {

            startPauseCounter++;
            cdStart = new CountDownTimer(storedTime, SECONDSPARAM) {
                @Override
                public void onTick(long l) {

                    countDownListener.countdownResult(l);
                    storedTime = l;
                }

                @Override
                public void onFinish() {

                }

            };
            cdStart.start();
        }
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
//    public void bindView(Object v, String tag){
//        viewList.add(v,tag);
//    }
//    public void unbindView(String tag){
//        viewList.unbindView(tag);
//    }



}
