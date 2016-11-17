package finalproject.csci205.com.countcown;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ceh024 on 11/16/16.
 */

public class CountDownView extends LinearLayout {

    private final int SECONDSPARAM = 1000;
    private TextView mins;
    private TextView seconds;

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
    }

    public void testCountDown() {
        final CountDownTimer cd = new CountDownTimer(minToMili(30), SECONDSPARAM) {
            @Override
            public void onTick(long l) {

                updateProgress(l);

            }

            @Override
            public void onFinish() {

            }

        };
        cd.start();

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


}
