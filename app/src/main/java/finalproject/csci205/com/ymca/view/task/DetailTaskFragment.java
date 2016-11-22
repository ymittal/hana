package finalproject.csci205.com.ymca.view.task;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.module.DetailTaskPresenter;
import finalproject.csci205.com.ymca.view.task.dialog.DatePickerDialog;
import finalproject.csci205.com.ymca.view.task.item.SimpleDividerItemDecoration;

import static finalproject.csci205.com.ymca.view.task.GTDFragment.REQUEST_CODE_QUICK;

/**
 * Created by ym012 on 11/16/2016.
 */
public class DetailTaskFragment extends Fragment {

    public static final String SERIALIZED_TASK = "SERIALIZED_TASK";

    private DetailTaskPresenter detailTaskPresenter;
    private Task task;
    private EditText etSubtask;
    private DatePicker datePicker;
    private Calendar calendar;
    private Button dateButton;
    private int year, month, day;

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

        initUI(root);
        initSubtaskList(root);

        return root;
    }

    /**
     * @param root
     * @see <a href="http://stackoverflow.com/questions/8233586/android-execute-function-after-pressing-enter-for-edittext">
     * </a>
     */
    private void initUI(final View root) {
        etSubtask = (EditText) root.findViewById(R.id.etSubtask);
        final TextInputLayout tilSubtask = (TextInputLayout) root.findViewById(R.id.tilSubtask);


        ImageView addSubtaskBtn = (ImageView) root.findViewById(R.id.addSubtaskBtn);
        addSubtaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tilSubtask.getVisibility() == View.GONE) {
                    tilSubtask.setVisibility(View.VISIBLE);
                    etSubtask.requestFocus();
                } else {
                    tilSubtask.setVisibility(View.GONE);
                }
            }
        });

        etSubtask.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    String sSubtask = etSubtask.getText().toString();
                    if (!sSubtask.equals("")) {
                        //Close the keyboard
                        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                        //Create the new subtask and add it to the UI
                        Subtask newSubtask = new Subtask(task.getId(), sSubtask);
                        detailTaskPresenter.addSubtask(newSubtask);
                        tilSubtask.setVisibility(View.GONE);
                        etSubtask.setText("");


                    }
                    return true;
                }
                return false;
            }
        });

        dateButton = (Button) root.findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog();
                dialog.setTargetFragment(dialog, REQUEST_CODE_QUICK);
                dialog.show(getFragmentManager(), "Select Date");
            }
        });


        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
    }

    private void initSubtaskList(View root) {
        RecyclerView rvSubtasks = (RecyclerView) root.findViewById(R.id.rvSubtasks);
        rvSubtasks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSubtasks.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        rvSubtasks.setAdapter(detailTaskPresenter.getSubtasksAdapter());
    }

    /**
     * Displays on the dateButton the given date in form MM/DD/YYYY
     *
     * @param year  Year to display
     * @param month Month to display
     * @param day   Day to display
     * @author Malachi
     */
    private void showDate(int year, int month, int day) {
        dateButton.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }
}