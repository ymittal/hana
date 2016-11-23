package finalproject.csci205.com.ymca.view.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.DetailTaskPresenter;
import finalproject.csci205.com.ymca.util.DateTimeUtil;
import finalproject.csci205.com.ymca.view.task.item.SimpleDividerItemDecoration;

/**
 * A fragment to display and let user update details of a {@link Task}
 */
public class DetailTaskFragment extends Fragment
        implements View.OnClickListener,
        View.OnKeyListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    /**
     * Name of {@link Task} serializable passed from {@link GTDFragment}
     */
    public static final String SERIALIZED_TASK = "SERIALIZED_TASK";
    /**
     * Number of degrees to rotate to open addSubtaskBtn
     */
    public static final int ROTATE_DEGREE_TO_OPEN = 45;
    /**
     * Number of degrees to rotate to close addSubtaskBtn
     */
    public static final int ROTATE_DEGREE_TO_CLOSE = 0;

    /**
     * Presenter for {@link DetailTaskFragment}
     */
    private DetailTaskPresenter detailTaskPresenter;
    /**
     * Task associated with current {@link DetailTaskFragment}
     */
    private Task task;
    /**
     * Calendar object to hold last task due date set by user
     */
    private Calendar myCalendar = Calendar.getInstance();

    // User Interface components of DetailTaskFragment
    private EditText etSubtask;
    private TextView tvDueDate;
    private ImageView addSubtaskBtn;
    private ImageView dummyBtn;

    /**
     * Required empty constructor
     */
    public DetailTaskFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Sets up fragment user interface and controls
     *
     * @param inflater           {@link LayoutInflater} to inflate views inside fragment
     * @param container          parent view encapsulating the fragment
     * @param savedInstanceState
     * @return view for the fragment interface
     * @author Malachi and Charles
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_task, container, false);
        root.setFocusableInTouchMode(true);
        root.requestFocus();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            task = (Task) bundle.getSerializable(SERIALIZED_TASK);
            getActivity().setTitle(task.getTitle());
            detailTaskPresenter = new DetailTaskPresenter(this, task);
        }

        setupInterfaceComponents(root);
        setReadableDueDate();
        initSubtaskList(root);

        return root;
    }

    /**
     * Initializes user interface elements and sets {@link android.view.View.OnClickListener}
     * and {@link android.view.View.OnKeyListener}
     *
     * @param root root view for the fragment interface
     * @author Malachi
     */
    private void setupInterfaceComponents(View root) {
        tvDueDate = (TextView) root.findViewById(R.id.tvDueDate);
        dummyBtn = (ImageView) root.findViewById(R.id.dummyBtn);
        addSubtaskBtn = (ImageView) root.findViewById(R.id.addSubtaskBtn);
        etSubtask = (EditText) root.findViewById(R.id.etSubtask);

        dummyBtn.setOnClickListener(this);
        addSubtaskBtn.setOnClickListener(this);
        etSubtask.setOnKeyListener(this);
    }

    /**
     * Sets readable due date if user has defined task due date already
     *
     * @author Yash
     */
    private void setReadableDueDate() {
        if (task.getDueDate() == null) {
            tvDueDate.setText("To be set");
        } else {
            tvDueDate.setText(DateTimeUtil.getReadableDate(task.getDueDate()));
            myCalendar.setTime(task.getDueDate());
        }
    }

    /**
     * Initializes recyclerview and hooks up {@link RecyclerView}
     * and {@link finalproject.csci205.com.ymca.view.task.item.SubtasksAdapter}, also adds
     * dividers between two list items
     *
     * @param root root view for the fragment interface
     */
    private void initSubtaskList(View root) {
        RecyclerView rvSubtasks = (RecyclerView) root.findViewById(R.id.rvSubtasks);
        rvSubtasks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSubtasks.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        rvSubtasks.setAdapter(detailTaskPresenter.getSubtasksAdapter());
    }

    /**
     * Handles click events on views implementing {@link android.view.View.OnClickListener}
     *
     * @param view clicked view
     * @author Yash and Malachi
     * @see <a href="http://stackoverflow.com/questions/30209415/rotate-an-imagewith-animation">
     * Stack Overflow - Rotate an Imagewith Animation</a>
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dummyBtn) {

            // shows a DatePicker dialog
            new DatePickerDialog(getContext(),
                    this,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        } else if (view.getId() == R.id.addSubtaskBtn) {

            // switches visibility and animates addSubtaskBtn
            if (etSubtask.getVisibility() == View.GONE) {
                addSubtaskBtn.animate().rotation(ROTATE_DEGREE_TO_OPEN).start();
                etSubtask.setVisibility(View.VISIBLE);
                etSubtask.requestFocus();
            } else {
                etSubtask.setVisibility(View.GONE);
                addSubtaskBtn.animate().rotation(ROTATE_DEGREE_TO_CLOSE).start();
            }

        }
    }

    /**
     * Handles key events on views implementing {@link android.view.View.OnKeyListener}
     *
     * @param view
     * @param i
     * @param keyEvent
     * @return
     * @author Yash
     * @see <a href="http://stackoverflow.com/questions/8233586/android-execute-function-after-pressing-enter-for-edittext">
     * Stack Overflow - Android execute function after pressing “Enter” for EditText</a>
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

            String subtask = etSubtask.getText().toString();
            if (!subtask.equals("")) {
                Subtask newSubtask = new Subtask(task.getId(), subtask);
                detailTaskPresenter.addSubtask(newSubtask);
                addSubtaskBtn.performClick();
                etSubtask.setText("");
            }
            return true;

        }
        return false;
    }

    /**
     * Callback when user picks a date in {@link DatePickerDialog}, shows a
     * {@link TimePickerDialog} straight away
     *
     * @param view        {@link DatePicker} object
     * @param year        year
     * @param monthOfYear month of year
     * @param dayOfMonth  day or month
     * @author Malachi
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        // shows a TimePicker dialog (12 hr display)
        new TimePickerDialog(getContext(),
                this,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                false).show();
    }

    /**
     * Callback when user picks a time in {@link TimePickerDialog}, sets task date through
     * {@link DetailTaskPresenter} and updates {@link DetailTaskFragment} due date {@link TextView}
     *
     * @param timePicker {@link TimePicker} object
     * @param hourOfDay  hour of day
     * @param minute     minute
     * @author Yash
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        detailTaskPresenter.setTaskDate(task, myCalendar.getTime());
        setReadableDueDate();
    }
}