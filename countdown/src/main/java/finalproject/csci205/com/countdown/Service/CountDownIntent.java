package finalproject.csci205.com.countdown.Service;

import android.content.Context;
import android.content.Intent;

import finalproject.csci205.com.countdown.Ults.Constants;

/**
 *
 * Custom intent that extends basic intent functionality
 * @author Charles
 *
 */
public class CountDownIntent extends Intent {

    /* Specifies the session param in MINS! */
    public CountDownIntent(Context c, int sessionTime) {
        super(c, CountDownService.class);
        this.putExtra(Constants.STRINGEXTRA, sessionTime);

    }

}
