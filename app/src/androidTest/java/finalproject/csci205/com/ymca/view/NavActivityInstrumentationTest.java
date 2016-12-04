package finalproject.csci205.com.ymca.view;

import android.content.Context;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.inputmethod.InputMethodManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Class to test {@link NavActivity} UI functionality
 *
 * @author Yash
 */
public class NavActivityInstrumentationTest {
    public static final String DUMMY_TASK = "Dummy Task";
    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    @Before
    public void setUp() throws Exception {
        Task.deleteAll(Task.class);
    }

    @After
    public void tearDown() throws Exception {
        Task.deleteAll(Task.class);
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

    /**
     * Checks whether {@link android.support.v4.widget.DrawerLayout}'s
     * {@link android.support.design.widget.NavigationView} gets hidden when it is open
     * and back buttoon is pressed
     *
     * @author Yash
     */
    @Test
    public void testOnBackPressedNavDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.nav_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    /**
     * Ensures that {@link finalproject.csci205.com.ymca.view.task.DetailTaskFragment} is closed
     * when back button is pressed
     *
     * @author Yash
     */
    @Test
    public void testOnBackPressedDetailTaskFrag() {
        String sEdit = activityTestRule.getActivity().getString(R.string.positive_btn_edit);
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.etAddTask)).perform(typeText(DUMMY_TASK), closeSoftKeyboard());
        onView(withText(sEdit)).perform(click());

        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.fragmentGTD)).check(matches(isDisplayed()));
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
    }

    /**
     * Tests whether soft keyboard hides successfully
     *
     * @author Yash
     */
    @Test
    public void testHideKeyboard() {
        onView(withId(R.id.fab)).perform(click());
        onView(isRoot()).perform(closeSoftKeyboard());

        InputMethodManager imm = (InputMethodManager)
                activityTestRule.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Assert.assertEquals(true, imm.isAcceptingText());
    }
}