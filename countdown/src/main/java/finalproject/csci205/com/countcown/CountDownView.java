package finalproject.csci205.com.countcown;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/******************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2016
 *
 * Name: YMCA
 * Date: Nov 1, 2016
 * Time: 7:50:26 PM
 *
 * Project: csci205_final
 * Package: finalproject.csci205.com.countcown
 * File: CountDownView
 * Description:
 * Homebrew CountDownView that defines a generic count down timer.
 * ****************************************
 */

/**
 * @author Charles
 */
public class CountDownView extends LinearLayout implements View.OnClickListener, CountDownListener {

    private final int SECONDSPARAM = 1000;
    private int startPauseCounter = 0;
    private long storedTime;
    private TextView mins;
    private TextView seconds;
    private ImageButton cancelPom;
    private LinearLayout timerContainer;
    private CountDownTimer cdStart = null;
    private CountDownListener countDownListener;


    public CountDownView(Context context) {
        super(context);
        init();
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    /**
     * Creates view elements
     *
     * @author Charles
     */
    public void init() {
        View root = inflate(getContext(), R.layout.countdownlayout, this);
        mins = (TextView) root.findViewById(R.id.mins);
        seconds = (TextView) root.findViewById(R.id.seconds);
        cancelPom = (ImageButton) root.findViewById(R.id.cancelBtn);
        cancelPom.setVisibility(GONE);
        timerContainer = (LinearLayout) root.findViewById(R.id.layoutContainer);
        timerContainer.setOnClickListener(this);
        cancelPom.setOnClickListener(this);
    }


    /**
     * Updates view to reflect accurate process
     *
     * @param l -  time
     * @author Charles
     */
    public void updateProgress(long l) {
        Date date = new Date(l);
        DateFormat minFor = new SimpleDateFormat("mm");
        DateFormat secFor = new SimpleDateFormat("ss");
        mins.setText(minFor.format(date));
        seconds.setText(secFor.format(date));
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


    @Override
    public void onClick(View view) {


        if (view.getId() == timerContainer.getId()) {
            cancelPom.setVisibility(VISIBLE);
            //startPauseCounter();
        } else if (view.getId() == cancelPom.getId()) {
            cdStart.cancel();
            mins.setText("30"); //TODO Make same int from settings
            seconds.setText("00");
            storedTime = minToMili(30);
            startPauseCounter++;
            cancelPom.setVisibility(GONE);
        }
    }

//    /**
//     * Starts/Stops counter depending on the state of the counter
//     * @author Charles
//     */
//    private void startPauseCounter() {
//        if (startPauseCounter == 0) {
//            startPauseCounter++;
//            //TODO get right param passing
//            cdStart = new CountDownTimer(minToMili(30), SECONDSPARAM) {
//                @Override
//                public void onTick(long l) {
//
//                    countdownResult(l);
//                    storedTime = l;
//                }
//
//                @Override
//                public void onFinish() {
//
//                }
//
//            };
//            cdStart.start();
//        } else if (startPauseCounter % 2 != 0) {
//            Toast.makeText(getContext(), "Paused!", Toast.LENGTH_SHORT).show();
//            startPauseCounter++;
//            cdStart.cancel();
//
//        } else if (startPauseCounter % 2 == 0) {
//            Toast.makeText(getContext(), "Resumed!", Toast.LENGTH_SHORT).show();
//            startPauseCounter++;
//            cdStart = new CountDownTimer(storedTime, SECONDSPARAM) {
//                @Override
//                public void onTick(long l) {
//
//                    countdownResult(l);
//                    storedTime = l;
//                }
//
//                @Override
//                public void onFinish() {
//
//                }
//
//            };
//            cdStart.start();
//        }
//    }

    @Override
    public void countdownResult(long l) {
        updateProgress(l);
    }



    /*
        TODO
        Make accessers to change the state of the view for breaks.
     */


}
