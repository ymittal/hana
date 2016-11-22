package finalproject.csci205.com.ymca.view.task;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import java.util.Calendar;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.module.DetailTaskPresenter;
import finalproject.csci205.com.ymca.view.task.item.SimpleDividerItemDecoration;

/**
 * Created by ym012 on 11/16/2016.
 */
public class DetailTaskFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static final String SERIALIZED_TASK = "SERIALIZED_TASK";
    Calendar myCalendar = Calendar.getInstance();
    private DetailTaskPresenter detailTaskPresenter;
    private Task task;

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

        initDatePicker(root);
        setupSubtaskControls(root);
        initSubtaskList(root);

        return root;
    }

    private void initDatePicker(View root) {
        ImageView dummyBtn = (ImageView) root.findViewById(R.id.dummyBtn);
        dummyBtn.setOnClickListener(this);
    }

    /**
     * @param root
     * @see <a href="http://stackoverflow.com/questions/8233586/android-execute-function-after-pressing-enter-for-edittext">
     * </a>
     */
    private void setupSubtaskControls(final View root) {
        final EditText etSubtask = (EditText) root.findViewById(R.id.etSubtask);
        final ImageView addSubtaskBtn = (ImageView) root.findViewById(R.id.addSubtaskBtn);

        etSubtask.setOnKeyListener(new View.OnKeyListener() {
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
        });

        addSubtaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etSubtask.getVisibility() == View.GONE) {
                    etSubtask.setVisibility(View.VISIBLE);
                    etSubtask.requestFocus();
                } else {
                    etSubtask.setVisibility(View.GONE);
                }
            }
        });

//        dateButton = (Button) root.findViewById(R.id.dateButton);
//        dateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MyDatePickerDialog dialog = new MyDatePickerDialog();
//                dialog.setTargetFragment(dialog, REQUEST_CODE_QUICK);
//                dialog.show(getFragmentManager(), "Select Date");
//            }
//        });
//
//
//        calendar = Calendar.getInstance();
//
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//        showDate(year, month + 1, day);
    }

    private void initSubtaskList(View root) {
        RecyclerView rvSubtasks = (RecyclerView) root.findViewById(R.id.rvSubtasks);
        rvSubtasks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSubtasks.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        rvSubtasks.setAdapter(detailTaskPresenter.getSubtasksAdapter());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Toast.makeText(getActivity(), myCalendar.getTime().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dummyBtn) {
            new DatePickerDialog(getContext(),
                    this,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

//    /**
//     * Displays on the dateButton the given date in form MM/DD/YYYY
//     *
//     * @param year  Year to display
//     * @param month Month to display
//     * @param day   Day to display
//     * @author Malachi
//     */
//    private void showDate(int year, int month, int day) {
//        dateButton.setText(new StringBuilder().append(month).append("/")
//                .append(day).append("/").append(year));
//    }
}