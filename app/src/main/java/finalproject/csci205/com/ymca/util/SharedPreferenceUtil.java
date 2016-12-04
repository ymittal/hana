package finalproject.csci205.com.ymca.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Utility class to access and modify {@link SharedPreferences}
 * @author Yash, Malachi, and Aleks
 */
public class SharedPreferenceUtil {

    /**
     * Constant for {@link SharedPreferences} whether user has started using the app
     */
    private static final String PREFS_IS_OPEN = "PREFS_IS_OPEN";

    /**
     * @param context context object
     * @return boolean denoting whether user has pressed begin button on first app start
     * @author Malachi and Aleks
     */
    public static boolean getIsOpen(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(PREFS_IS_OPEN, false);
    }

    /**
     * Sets {@link SharedPreferences} for whether user has pressed begin button on first app start
     *
     * @param context context
     * @param isOpen  whether user pressed the begin button
     * @author Malachi and Aleks
     */
    public static void setPreferenceIsOpen(Context context, boolean isOpen) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PREFS_IS_OPEN, isOpen);
        editor.apply();
    }
}
