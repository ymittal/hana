package finalproject.csci205.com.countcown;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ceh024 on 11/16/16.
 */

public class CountDownView extends LinearLayout implements View.OnClickListener {

    private final int SECONDSPARAM = 1000;
    ImageButton cancelPom;
    private int startPauseCounter = 0;
    private long storedTime;
    private TextView mins;
    private TextView seconds;
    private LinearLayout timerContainer;
    private CountDownTimer cdStart = null;

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


    public long minToMili(int min) {
        return min * 60000;
    }


    @Override
    public void onClick(View view) {


        if (view.getId() == timerContainer.getId()) {
            cancelPom.setVisibility(VISIBLE);
            startPauseCounter();
        } else if (view.getId() == cancelPom.getId()) {
            cdStart.cancel();
            mins.setText("30"); //TODO Make same int from settings
            seconds.setText("00");
            storedTime = minToMili(30);
            startPauseCounter++;
            cancelPom.setVisibility(GONE);
        }
    }

    private void startPauseCounter() {
        if (startPauseCounter == 0) {
            startPauseCounter++;
            //TODO get right param passing
            cdStart = new CountDownTimer(minToMili(30), SECONDSPARAM) {
                @Override
                public void onTick(long l) {

                    updateProgress(l);
                    storedTime = l;
                }

                @Override
                public void onFinish() {

                }

            };
            cdStart.start();
        } else if (startPauseCounter % 2 != 0) {
            Toast.makeText(getContext(), "Paused!", Toast.LENGTH_SHORT).show();
            startPauseCounter++;
            cdStart.cancel();

        } else if (startPauseCounter % 2 == 0) {
            Toast.makeText(getContext(), "Resumed!", Toast.LENGTH_SHORT).show();
            startPauseCounter++;
            cdStart = new CountDownTimer(storedTime, SECONDSPARAM) {
                @Override
                public void onTick(long l) {

                    updateProgress(l);
                    storedTime = l;
                }

                @Override
                public void onFinish() {

                }

            };
            cdStart.start();
        }
    }


}
