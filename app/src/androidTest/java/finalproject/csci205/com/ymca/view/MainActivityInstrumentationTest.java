package finalproject.csci205.com.ymca.view;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.TestUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Class to test {@link MainActivity} UI functionality
 *
 * @author Yash
 */
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Waits for specific number of milliseconds before continuing the test
     *
     * @param millis number of milliseconds to wait
     * @return {@link ViewAction} object
     * @see <a href="http://stackoverflow.com/questions/21417954/espresso-thread-sleep">
     * Stack Overflow - Espresso: Thread.sleep( );</a>
     */
    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Tests {@link MainActivity#onClick(View)} by checking whether
     * {@link finalproject.csci205.com.ymca.view.task.GTDFragment} opens successfully on
     * app start; also tests {@link MainActivity#goToNavActivity()}
     *
     * @throws Exception
     * @author Yash
     */
    @Test
    public void onClick() {
        try {
            // user has not started using the application
            String sBeginButton = activityTestRule.getActivity().getString(R.string.beginButton);
            onView(withText(sBeginButton)).check(matches(isDisplayed()));
            onView(isRoot()).perform(waitFor(MainActivity.ANIMATION_DURATION_MILLIS));
            onView(withText(sBeginButton)).perform(click());
            onView(withId(R.id.fragmentGTD)).check(matches(isDisplayed()));

        } catch (AssertionFailedError | NoMatchingViewException e) {
            onView(isRoot()).perform(waitFor(MainActivity.DELAY_MILLIS));
            onView(withId(R.id.fragmentGTD)).check(matches(isDisplayed()));

        }
    }

    /**
     * Tests whether {@link MainActivity} background is loaded as one of the three images
     *
     * @author Yash
     */
    @Test
    public void testLoadBackground() {
        try {
            onView(withId(R.id.relativeLayout)).check(matches(TestUtil.withBackground(R.drawable.splash_cliff)));
        } catch (AssertionFailedError e1) {
            try {
                onView(withId(R.id.relativeLayout)).check(matches(TestUtil.withBackground(R.drawable.splash_notebook)));
            } catch (AssertionFailedError e2) {
                onView(withId(R.id.relativeLayout)).check(matches(TestUtil.withBackground(R.drawable.splash_map)));
            }
        }
    }
}