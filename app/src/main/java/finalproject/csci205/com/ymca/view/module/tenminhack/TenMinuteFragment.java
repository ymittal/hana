package finalproject.csci205.com.ymca.view.module.tenminhack;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import finalproject.csci205.com.ymca.R;

import static android.content.Context.ALARM_SERVICE;


public class TenMinuteFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private Calendar myCalendar = Calendar.getInstance();

    private TextView tvClock;

    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;




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

        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        tvClock = (TextView) root.findViewById(R.id.tvClock);
        tvClock.setOnClickListener(this);

        //TODO: Get saved time from sugar
        // textClock.setText("The set time");
        updateTvClock(myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE));

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvClock) {
            // shows a TimePicker dialog (12 hr display)
            new TimePickerDialog(getContext(),
                    this,
                    myCalendar.get(Calendar.HOUR_OF_DAY),
                    myCalendar.get(Calendar.MINUTE),
                    false).show();
        } else if (view.getId() == R.id.alarmSwitch) {
            //TODO: change SharedPreferences to alarm on or off
        }
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);

        updateTvClock(hourOfDay, minute);

        Intent intent = new Intent(TenMinuteFragment.this.getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(TenMinuteFragment.this.getActivity(), 0, intent, 0);
        alarmManager.set(AlarmManager.RTC, myCalendar.getTimeInMillis(), pendingIntent);

    }

    /**
     * Updates the clock TextView on the UI to represent the given time
     *
     * @param hourOfDay The hour of the day (in 24-hour mode) to be displayed.
     * @param minute    The minute of the day to be displayed
     * @return String representation that is displayed on the clock TextView
     * @author Malachi
     */
    private String updateTvClock(int hourOfDay, int minute) {
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
                .append(":");
        //checking if a '0' needs to be placed in front of the single_digit minute
        if (minute < 10) {
            stringBuilder.append("0" + minute);
        } else {
            stringBuilder.append(minute);
        }

        //Checking if the user wants an AM or PM alarm
        if (isAM) {
            stringBuilder.append("AM");
        } else {
            stringBuilder.append("PM");
        }

        tvClock.setText(stringBuilder.toString());

        return stringBuilder.toString();
    }
}
