package finalproject.csci205.com.ymca.view.module.pomodoro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import finalproject.csci205.com.countdown.View.CountDownView;
import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.presenter.module.PomodoroPresenter;
import finalproject.csci205.com.ymca.view.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;


public class PomodoroFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private CountDownView countDownView;

    public PomodoroFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PomodoroFragment.
     */

    public static PomodoroFragment newInstance() {
        PomodoroFragment fragment = new PomodoroFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Pomodoro");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PomodoroPresenter pomodoroPresenter = new PomodoroPresenter(this);
        View root = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        countDownView = (CountDownView) root.findViewById(R.id.countDownViewInFragment);
        countDownView.setSessionTime(1);//TODO GET FROM MODEL -- > PRESENTER
        countDownView.setJumpTo(MainActivity.class);
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == countDownView.getId()) {
            Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
        }
    }

    public void test(Context context) {


        // prepare intent which is triggered if the
        // notification is selected

        //Intent intent = new Intent(this, NotificationReceiver.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), new Intent(), 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n = new Notification.Builder(context)
                .setContentTitle("Time Remaining")
                .setContentText("28 : 00")
                .setSmallIcon(R.drawable.ic_pom)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_action_add, "Start", pIntent)
                .addAction(R.drawable.ic_pom, "Pause", pIntent)
                .addAction(R.drawable.ic_cancel, "Cancel", pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
