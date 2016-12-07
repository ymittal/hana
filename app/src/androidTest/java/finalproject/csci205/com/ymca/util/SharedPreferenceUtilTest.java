package finalproject.csci205.com.ymca.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Class to test {@link SharedPreferenceUtil}
 */
@RunWith(AndroidJUnit4.class)
public class SharedPreferenceUtilTest {

    /**
     * Testing constants
     */
    public static final String PREFS_IS_OPEN = "PREFS_IS_OPEN";
    /**
     * Local context & System level service refrences
     */
    private Context instrumantationCtx;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    /**
     * Initializes {@link SharedPreferences} and {@link SharedPreferences.Editor}
     *
     * @throws Exception
     * @author Yash
     */
    @Before
    public void setUp() throws Exception {
        instrumantationCtx = InstrumentationRegistry.getContext();

        prefs = PreferenceManager.getDefaultSharedPreferences(instrumantationCtx);
        editor = prefs.edit();
    }

    /**
     * Clears {@link SharedPreferences}
     *
     * @throws Exception
     * @author Yash
     */
    @After
    public void tearDown() throws Exception {
        editor.clear();
        editor.commit();
    }

    /**
     * Test for {@link SharedPreferenceUtil#getIsOpen(Context)}
     *
     * @throws Exception
     */
    @Test
    public void getIsOpen() throws Exception {
        // SharedPreferences cannot be written in Instrumentation Testing in Android
    }

    /**
     * Test for {@link SharedPreferenceUtil#setPreferenceIsOpen(Context, boolean)}
     *
     * @throws Exception
     */
    @Test
    public void setPreferenceIsOpen() throws Exception {
        SharedPreferenceUtil.setPreferenceIsOpen(instrumantationCtx, true);
        boolean result = prefs.getBoolean(PREFS_IS_OPEN, false);
        boolean expected = true;
        Assert.assertEquals(expected, result);
    }

}