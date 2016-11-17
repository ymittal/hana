package finalproject.csci205.com.ymca.view.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Alekzandar on 11/16/16.
 */

public class SharedPreferenceUtil {

    public static boolean getIsOpen(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("PREFS_IS_OPEN", false);
    }

    public static void setPreferenceIsOpen(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("PREFS_IS_OPEN", true);
        editor.apply();

    }
}
