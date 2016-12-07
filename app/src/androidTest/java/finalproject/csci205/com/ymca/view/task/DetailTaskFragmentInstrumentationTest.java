package finalproject.csci205.com.ymca.view.task;

import android.content.Context;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import junit.framework.Assert;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.util.DateTimeUtil;
import finalproject.csci205.com.ymca.view.NavActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Class to test {@link DetailTaskFragment} UI functionality
 *
 * @author Yash
 */
public class DetailTaskFragmentInstrumentationTest {

    /**
     * Testing constants
     */
    public static final String DUMMY_TASK = "Dummy Task";
    public static final String DUMMY_SUBTASK = "Dummy Subtask";
    public static final String PICKER_OK_BTN = "OK";
    public static final String PICKER_CANCEL_BTN = "Cancel";

    @Rule
    public ActivityTestRule<NavActivity> activityTestRule = new ActivityTestRule<>(NavActivity.class);

    public Calendar myCalendar = Calendar.getInstance();

    /**
     * Automates adding a {@link finalproject.csci205.com.ymca.model.Task} and launching
     * the {@link DetailTaskFragment} by clicking on the first {@link RecyclerView} item.
     * Also sets up a {@link Calendar} object to test {@link android.app.DatePickerDialog}
     * and {@link android.app.TimePickerDialog} functionality
     * Tests {@link finalproject.csci205.com.ymca.presenter.GTDPresenter#openDetailedTaskFragment(Task)}
     *
     * @throws Exception
     * @author Yash
     */
    @Before
    public void setUp() throws Exception {
        Task.deleteAll(Task.class);
        Subtask.deleteAll(Subtask.class);

        String sSave = activityTestRule.getActivity().getString(R.string.negative_btn_save);
        onView(withId(R.id.fab)).perform(click());
        onView(withText(sSave)).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withId(R.id.etAddTask)).perform(typeText(DUMMY_TASK), closeSoftKeyboard());
        onView(withText(sSave)).perform(click());
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // set to December 1, 2016 (6:30 pm)
        myCalendar.set(Calendar.YEAR, 2016);
        myCalendar.set(Calendar.MONTH, 12);
        myCalendar.set(Calendar.DAY_OF_MONTH, 1);
        myCalendar.set(Calendar.HOUR_OF_DAY, 18);
        myCalendar.set(Calendar.MINUTE, 30);
    }

    /**
     * Automates closing the keyboard and going back to {@link GTDFragment} to delete the
     * recently added {@link finalproject.csci205.com.ymca.model.Task}
     *
     * @throws Exception
     * @author Yash
     */
    @After
    public void tearDown() throws Exception {
        onView(withId(R.id.fragmentDetailTask)).check(matches(isDisplayed()));
        onView(withId(R.id.fragmentDetailTask)).perform(closeSoftKeyboard());
        onView(withId(R.id.fragmentDetailTask)).perform(ViewActions.pressBack());
        onView(withId(R.id.rvTasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));

        Task.deleteAll(Task.class);
        Subtask.deleteAll(Subtask.class);
    }

    /**
     * Tests whether a {@link finalproject.csci205.com.ymca.model.Subtask} is successfully
     * added to the list of subtasks under {@link finalproject.csci205.com.ymca.model.Task}
     *
     * @author Yash
     */
    @Test
    public void checkAddSubtask() {
        onView(withId(R.id.rvSubtasks)).check(new RecyclerViewItemCountAssertion(0));
        onView(withId(R.id.addSubtaskBtn)).perform(click());

        onView(withId(R.id.etSubtask)).perform(typeText("")).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.rvSubtasks)).check(new RecyclerViewItemCountAssertion(0));

        onView(withId(R.id.etSubtask)).perform(typeText(DUMMY_SUBTASK)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.rvSubtasks)).check(new RecyclerViewItemCountAssertion(1));
    }

    /**
     * Tests whether a {@link finalproject.csci205.com.ymca.model.Subtask} is removed
     * when its corresponding cancel button is clicked
     *
     * @author Yash
     */
    @Test
    public void checkRemoveSubtask() {
        checkAddSubtask();
        onView(withId(R.id.btnCancel)).perform(click());
        onView(withId(R.id.rvSubtasks)).check(new RecyclerViewItemCountAssertion(0));
    }

    /**
     * Tests whether {@link java.util.Date} and {@link java.sql.Time} of
     * {@link finalproject.csci205.com.ymca.model.Task#dueDate} is set appropriately
     *
     * @throws InterruptedException
     * @author Yash
     */
    @Test
    public void testSaveDueDateTime() throws InterruptedException {
        onView(withId(R.id.tvDueDate)).perform(click());
        setDate();
        setTime();

        onView(withId(R.id.fragmentDetailTask)).check(matches(isDisplayed()));
        onView(withId(R.id.tvDueDate)).check(matches(withText(DateTimeUtil.getReadableDate(myCalendar.getTime()))));
    }

    /**
     * Tests cancelling of {@link android.app.DatePickerDialog} in {@link DetailTaskFragment}
     *
     * @author Yash
     */
    @Test
    public void testSaveEmptyDate() {
        onView(withId(R.id.tvDueDate)).perform(click());

        onView(withText(PICKER_CANCEL_BTN)).perform(click());
        onView(withId(R.id.fragmentDetailTask)).check(matches(isDisplayed()));
        onView(withId(R.id.tvDueDate)).check(matches(withText(R.string.tv_due_date)));
    }

    /**
     * Tests cancelling of {@link android.app.TimePickerDialog} in {@link DetailTaskFragment}
     *
     * @author Yash
     */
    @Test
    public void testSaveEmptyTime() {
        onView(withId(R.id.tvDueDate)).perform(click());
        setDate();

        onView(withText(PICKER_CANCEL_BTN)).perform(click());
        onView(withId(R.id.fragmentDetailTask)).check(matches(isDisplayed()));
        onView(withId(R.id.tvDueDate)).check(matches(withText(R.string.tv_due_date)));
    }

    /**
     * Sets {@link java.util.Date} in {@link android.app.DatePickerDialog}
     *
     * @author Yash
     */
    private void setTime() {
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE))
                );
        onView(withText(PICKER_OK_BTN)).perform(click());
    }

    /**
     * Sets {@link java.sql.Time} in {@link android.app.TimePickerDialog}
     *
     * @author Yash
     */
    private void setDate() {
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH) + 1,
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                );
        onView(withText(PICKER_OK_BTN)).perform(click());
    }

    /**
     * Tests whether soft keyboard gets closed when user taps outside current EditText
     * with focus; basically tests {@link NavActivity#dispatchTouchEvent(MotionEvent)} method
     *
     * @author Yash
     */
    @Test
    public void testCloseKeyboardTapOutside() {
        onView(withId(R.id.addSubtaskBtn)).perform(click());
        onView(withId(R.id.etSubtask)).perform(typeText(DUMMY_SUBTASK));
        onView(withId(R.id.tvDueAt)).perform(click());  // simulates a click outside Subtask EditText

        InputMethodManager imm = (InputMethodManager)
                activityTestRule.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Assert.assertEquals(true, imm.isAcceptingText());
    }

}