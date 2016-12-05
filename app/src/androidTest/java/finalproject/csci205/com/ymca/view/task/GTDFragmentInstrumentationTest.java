package finalproject.csci205.com.ymca.view.task;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.view.NavActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Class to test {@link GTDFragment} UI functionality
 *
 * @author Yash
 */
public class GTDFragmentInstrumentationTest {

    public static final String DUMMY_TASK = "Dummy Task";

    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    /**
     * Automates adding a {@link finalproject.csci205.com.ymca.model.Task} quickly through
     * {@link finalproject.csci205.com.ymca.view.task.dialog.AddQuickTaskDialog}
     *
     * @throws Exception
     * @author Yash
     */
    @Before
    public void setUp() throws Exception {
        Task.deleteAll(Task.class);

        String sSave = activityTestRule.getActivity().getString(R.string.negative_btn_save);
        onView(withId(R.id.fab)).perform(click());
        onView(withText(sSave)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withId(R.id.etAddTask)).perform(typeText(DUMMY_TASK), closeSoftKeyboard());
        onView(withText(sSave)).perform(click());
        onView(withId(R.id.rvTasks)).check(matches(hasDescendant(withText(DUMMY_TASK))));
    }

    /**
     * Automates deleting the recently added {@link finalproject.csci205.com.ymca.model.Task}
     * from list of task
     *
     * @throws Exception
     * @author Yash
     */
    @After
    public void tearDown() throws Exception {
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        onView(withId(R.id.rvTasks)).check(new RecyclerViewItemCountAssertion(0));

        Task.deleteAll(Task.class);
    }

    /**
     * Test to restore {@link finalproject.csci205.com.ymca.model.Task} by clicking on
     * action button of {@link android.support.design.widget.Snackbar}
     *
     * @author Yash
     * @see <a href="http://stackoverflow.com/questions/33381366/how-to-click-the-snackbar-button-in-espresso-testing">
     * Stack Overflow - How to click the snackbar button in Espresso testing?</a>
     */
    @Test
    public void testRestoreTask() {
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        onView(allOf(withId(android.support.design.R.id.snackbar_action))).perform(click());
        onView(withId(R.id.rvTasks)).check(new RecyclerViewItemCountAssertion(1));
    }

    /**
     * Ensures that {@link finalproject.csci205.com.ymca.view.task.dialog.AddQuickTaskDialog}
     * shows up when {@link android.support.design.widget.FloatingActionButton} is tapped
     *
     * @author Yash
     */
    @Test
    public void testOnFabClick() {
        String sSave = activityTestRule.getActivity().getString(R.string.negative_btn_save);
        onView(withId(R.id.fab)).perform(click());
        onView(withText(sSave)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText(sSave)).perform(closeSoftKeyboard(), pressBack());
    }

}