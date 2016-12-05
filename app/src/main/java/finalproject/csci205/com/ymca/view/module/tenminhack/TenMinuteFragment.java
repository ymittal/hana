package finalproject.csci205.com.ymca.view.module.tenminhack;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import finalproject.csci205.com.ymca.R;

/**
 * Created by Malachi on 12/2/2016.
 */
public class TenMinuteFragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    /**
     * Key to save user-set alarm time
     */
    protected static final String PREF_TENMIN_ALARM_MILLI_KEY = "PREF_TENMIN_ALARM_MILLI";
    /**
     * Key to save the state of the alarm toggle
     */
    protected static final String PREF_TENMIN_ALARM_TOGGLE_KEY = "PREF_TENMIN_ALARM_TOGGLE";

    /**
     * Necessary tools of the trade
     */
    private Calendar myCalendar;
    private TextView tvClock;
    private Switch alarmSwitch;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    /**
     * Required empty constructor
     */
    public TenMinuteFragment() {
    }

    /**
     * Use this factor method to create a new instance of {@link TenMinuteFragment}
     *
     * @return A new instance of {@link TenMinuteFragment}
     * @author Yash
     */
    public static TenMinuteFragment newInstance() {
        TenMinuteFragment fragment = new TenMinuteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * A method called within the activity lifecycle. Comes before onCreateView, but after onStart
     *
     * @param savedInstanceState
     * @author Malachi
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myCalendar = Calendar.getInstance();
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//commit() is done later...
    }

    /**
     * The method called within the activity lifecycle. Comes after onCreate
     *
     * @param inflater
     * @param container
     * @param saveInstanceState
     * @return
     * @author Malachi
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        //set the title of the view
        getActivity().setTitle("10-Minute Hack");

        //instantiate variables
        View root = inflater.inflate(R.layout.fragment_tenmin, container, false);
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        tvClock = (TextView) root.findViewById(R.id.tvClock);
        alarmSwitch = (Switch) root.findViewById(R.id.alarmSwitch);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        //set on click listeners for the views I want to clickable
        tvClock.setOnClickListener(this);
        alarmSwitch.setOnClickListener(this);

        //if the user had set the alarm previously, update the calendar to this alarm's time
        if (sharedPreferences.getLong(PREF_TENMIN_ALARM_MILLI_KEY, 0) != 0) {
            myCalendar.setTimeInMillis(sharedPreferences.getLong(PREF_TENMIN_ALARM_MILLI_KEY, 0));
        }

        //if the user has the alarm toggled on, update the ui
        if (sharedPreferences.getBoolean(PREF_TENMIN_ALARM_TOGGLE_KEY, false)) {
            alarmSwitch.setChecked(true);
        }
        //Update the clock with whatever the calendar is set to
        updateTvClock(myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE));

        return root;
    }

    /**
     * When any view within this fragment is clicked, this method is called
     *
     * @param view The view within the fragment that was clicked
     * @author Malachi
     */
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

            if (alarmSwitch.isChecked()) {
                //if the switch was toggled on, set the alarm, let the user know, and save the state of the toggle
                Toast.makeText(getContext(), "Alarm on", Toast.LENGTH_SHORT).show();
                editor.putBoolean(PREF_TENMIN_ALARM_TOGGLE_KEY, true);
                editor.commit();
                onTimeSet(null, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE));
            } else {
                //else, the alarm was turned off, so cancel the alarm, let the user know, and save the state of the toggle
                Toast.makeText(getContext(), "Alarm off", Toast.LENGTH_SHORT).show();
                alarmManager.cancel(pendingIntent);
                editor.putBoolean(PREF_TENMIN_ALARM_TOGGLE_KEY, false);
                editor.commit();
            }
        }


    }

    /**
     * Called when the "ok" has been hit in the time picker
     *
     * @param timePicker The timePicker object that called this method
     * @param hourOfDay  The hour of day picked in 24-hour mode
     * @param minute     The minute of the day from within the given hour
     * @author Malachi
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        //update the underlying calendar
        myCalendar.setTimeInMillis(System.currentTimeMillis());
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);

        //update the view
        updateTvClock(hourOfDay, minute);

        //update the database
        editor.putLong(PREF_TENMIN_ALARM_MILLI_KEY, myCalendar.getTimeInMillis());
        editor.commit();
        if (sharedPreferences.getBoolean(PREF_TENMIN_ALARM_TOGGLE_KEY, false)) {
            //Make the alarm. If the user doesn't have the toggle selected, the alarm won't be set
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    /**
     * Updates the clock TextView on the UI to represent the given time
     *
     * @param hourOfDay The hour of the day (in 24-hour mode) to be displayed.
     * @param minute    The minute of the day to be displayed
     * @return String representation that is displayed on the clock TextView. Just a ref, nothing needed to be done with it
     * @author Malachi
     */
    private String updateTvClock(int hourOfDay, int minute) {
        boolean isAM;

        //Since hourOfDay is in 24-hour mode, convert to 12 hour mode appropriately
        if (hourOfDay <= 12) {
            isAM = true;
            if (hourOfDay == 0) {
                hourOfDay = 12;
            }
        } else {
            isAM = false;
            hourOfDay = hourOfDay - 12;
        }

        //Make the text that is to be displayed
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(hourOfDay)
                .append(":");
        //checking if a '0' needs to be placed in front of the single_digit minute
        if (minute < 10) {
            stringBuilder.append("0").append(minute);
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
