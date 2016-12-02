package finalproject.csci205.com.ymca.view.module.tenminhack;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TimePicker;

import java.util.Calendar;

import finalproject.csci205.com.ymca.R;


public class TenMinuteFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    Calendar myCalendar = Calendar.getInstance();

    TextClock textClock;


    /**
     * Required empty constructor
     */
    public TenMinuteFragment() {
    }

    /**
     * Use this factor method to create a new instance of {@link TenMinuteFragment}
     *
     * @return A new instance of {@link TenMinuteFragment}
     */
    public static TenMinuteFragment newInstance() {
        TenMinuteFragment fragment = new TenMinuteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Ten Minute Hack");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tenmin, container, false);
        getActivity().setTitle("10-Minute Hack");
        textClock = (TextClock) root.findViewById(R.id.textClock);

        //TODO: Get saved time from sugar
        // textClock.setText("The set time");

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.textClock) {
            // shows a TimePicker dialog (12 hr display)
            new TimePickerDialog(getContext(),
                    this,
                    myCalendar.get(Calendar.HOUR_OF_DAY),
                    myCalendar.get(Calendar.MINUTE),
                    false).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        boolean isAM;
        if (hourOfDay <= 12) {
            isAM = true;
        } else {
            isAM = false;
            hourOfDay = hourOfDay - 12;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(hourOfDay)
                .append(":")
                .append(minute);
        if (isAM) {
            stringBuilder.append("AM");
        } else {
            stringBuilder.append("PM");
        }

        String newTime = stringBuilder.toString();
        textClock.setText(newTime);
    }
}
