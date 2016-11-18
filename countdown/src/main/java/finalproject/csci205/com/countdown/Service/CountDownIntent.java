package finalproject.csci205.com.countdown.Service;

import android.content.Context;
import android.content.Intent;

import finalproject.csci205.com.countdown.Ults.Constants;

/******************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2016
 * <p>
 * Name: YMCA
 * Date: Nov 1, 2016
 * Time: 7:50:26 PM
 * <p>
 * Project: csci205_final
 * Package: finalproject.csci205.com.countdown
 * File: CountDownView
 * Description:
 * Specific intent for this lib
 * ****************************************
 */

/**
 * @author Charles
 */
public class CountDownIntent extends Intent {

    /* Specifies the session param in MINS! */
    public CountDownIntent(Context c, int sessionTime) {
        super(c, CountDownService.class);
        this.putExtra(Constants.STRINGEXTRA, sessionTime);

    }

}
