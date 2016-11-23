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
 * Created by ym012 on 11/16/2016.
 */
public class DetailTaskFragment extends Fragment
        implements View.OnClickListener,
        View.OnKeyListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    public static final String SERIALIZED_TASK = "SERIALIZED_TASK";
    public static final int ROTATE_DEGREE_TO_OPEN = 45;
    public static final int ROTATE_DEGREE_TO_CLOSE = 0;
    private DetailTaskPresenter detailTaskPresenter;
    private Task task;

    private Calendar myCalendar = Calendar.getInstance();

    private EditText etSubtask;
    private ImageView addSubtaskBtn;
    private ImageView dummyBtn;
    private TextView tvDueDate;

    public DetailTaskFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

    private void setupInterfaceComponents(View root) {
        tvDueDate = (TextView) root.findViewById(R.id.tvDueDate);
        dummyBtn = (ImageView) root.findViewById(R.id.dummyBtn);
        addSubtaskBtn = (ImageView) root.findViewById(R.id.addSubtaskBtn);
        etSubtask = (EditText) root.findViewById(R.id.etSubtask);

        dummyBtn.setOnClickListener(this);
        addSubtaskBtn.setOnClickListener(this);
        etSubtask.setOnKeyListener(this);
    }

    private void setReadableDueDate() {
        if (task.getDueDate() == null) {
            tvDueDate.setText("To be set");
        } else {
            tvDueDate.setText(DateTimeUtil.getReadableDate(task.getDueDate()));
            myCalendar.setTime(task.getDueDate());
        }
    }

    private void initSubtaskList(View root) {
        RecyclerView rvSubtasks = (RecyclerView) root.findViewById(R.id.rvSubtasks);
        rvSubtasks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSubtasks.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        rvSubtasks.setAdapter(detailTaskPresenter.getSubtasksAdapter());
    }

    /**
     * @param view
     * @see <a href="http://stackoverflow.com/questions/30209415/rotate-an-imagewith-animation">
     * Stack Overflow - Rotate an Imagewith Animation</a>
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dummyBtn) {
            new DatePickerDialog(getContext(),
                    this,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        } else if (view.getId() == R.id.addSubtaskBtn) {
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
     * @see <a href="http://stackoverflow.com/questions/8233586/android-execute-function-after-pressing-enter-for-edittext">
     * </a>
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
            String sSubtask = etSubtask.getText().toString();
            if (!sSubtask.equals("")) {
                Subtask newSubtask = new Subtask(task.getId(), sSubtask);
                detailTaskPresenter.addSubtask(newSubtask);
                addSubtaskBtn.performClick();
                etSubtask.setText("");
            }
            return true;
        }
        return false;
    }

    /**
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @author Malachi
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        new TimePickerDialog(getContext(),
                this,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                false).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        detailTaskPresenter.setTaskDate(task, myCalendar.getTime());
        setReadableDueDate();
    }
}