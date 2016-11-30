package finalproject.csci205.com.ymca.view;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import finalproject.csci205.com.ymca.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Class to test {@link NavActivity} UI functionality
 *
 * @author Yash
 */
public class NavActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Tests whether {@link android.support.design.widget.NavigationView} navigation to
     * fragments works fine
     *
     * @author Yash
     */
    @Test
    public void testNavDrawerOpen() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withText(activityTestRule.getActivity().getString(R.string.menuitem_pomodoro))).perform(click());
        onView(withId(R.id.fragmentPomodoro)).check(matches(isDisplayed()));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withText(activityTestRule.getActivity().getString(R.string.menuitem_tasks))).perform(click());
        onView(withId(R.id.fragmentGTD)).check(matches(isDisplayed()));
    }
}