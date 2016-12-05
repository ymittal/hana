package finalproject.csci205.com.ymca.view.task.dialog;

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.TestUtil;
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
import static org.hamcrest.Matchers.is;

/**
 * Class to test {@link AddQuickTaskDialog} UI functionality
 *
 * @author Yash
 */
public class AddQuickTaskDialogInstrumentationTest {

    public static final String DUMMY_TASK = "Dummy Task";

    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    private String sSave;
    private String sEdit;

    /**
     * Automates click on {@link android.support.design.widget.FloatingActionButton}
     * present on the {@link NavActivity}
     *
     * @throws Exception
     * @author Yash
     */
    @Before
    public void setUp() throws Exception {
        Task.deleteAll(Task.class);

        sSave = activityTestRule.getActivity().getString(R.string.negative_btn_save);
        sEdit = activityTestRule.getActivity().getString(R.string.positive_btn_edit);

        onView(withId(R.id.fab)).perform(click());
        onView(withText(sSave)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        Task.deleteAll(Task.class);
    }

    /**
     * Tests whether {@link finalproject.csci205.com.ymca.view.task.DetailTaskFragment} opens up
     * successfully when user enters a {@link finalproject.csci205.com.ymca.model.Task} title
     *
     * @author Yash
     */
    @Test
    public void checkAddDetailTask() {
        onView(withId(R.id.etAddTask)).perform(typeText(DUMMY_TASK), closeSoftKeyboard());
        onView(withText(sEdit)).perform(click());
        onView(withId(R.id.fragmentDetailTask)).check(matches(isDisplayed()));
        onView(withId(R.id.fragmentDetailTask)).perform(ViewActions.pressBack());
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
    }

    /**
     * Test to check whether a {@link finalproject.csci205.com.ymca.model.Task} is quickly
     * added through {@link AddQuickTaskDialog}
     */
    @Test
    public void checkQuickAddTask() {
        onView(withId(R.id.etAddTask)).perform(typeText(DUMMY_TASK), closeSoftKeyboard());
        onView(withText(sSave)).perform(click());
        onView(withId(R.id.rvTasks)).check(matches(hasDescendant(withText(DUMMY_TASK))));
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
    }

    /**
     * Validates user input and checks if {@link TextInputLayout} has an error when user enters
     * an empty {@link finalproject.csci205.com.ymca.model.Task} title for a quick task
     */
    @Test
    public void validateAddTaskEditText() {
        onView(withText(sSave)).perform(click());
        onView(withId(R.id.tilAddTask)).check(
                matches(TestUtil.withErrorInInputLayout(is(activityTestRule.getActivity().getString(R.string.error_til_add_task))))
        );
        onView(withText(sSave)).perform(closeSoftKeyboard(), pressBack());
    }

    /**
     * Validates user input and checks if {@link TextInputLayout} has an error when user enters
     * an empty {@link finalproject.csci205.com.ymca.model.Task} title for a detailed task
     */
    @Test
    public void validateAddDetailTaskEditText() {
        onView(withText(sEdit)).perform(click());
        onView(withId(R.id.tilAddTask)).check(
                matches(TestUtil.withErrorInInputLayout(is(activityTestRule.getActivity().getString(R.string.error_til_add_task))))
        );
        onView(withText(sSave)).perform(closeSoftKeyboard(), pressBack());
    }
}