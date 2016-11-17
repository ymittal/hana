package finalproject.csci205.com.countcown;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ceh024 on 11/16/16.
 */

public class CountDownView extends RelativeLayout {

    private TextView textView;

    public CountDownView(Context context) {
        super(context);
        init();
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void updateProgress(int startTime, int endTime) {
        textView.setText(String.valueOf(endTime - startTime));
    }

    public void init() {
        View root = inflate(getContext(), R.layout.countdownlayout, this);
        textView = (TextView) root.findViewById(R.id.countDownTextView);
    }


}
