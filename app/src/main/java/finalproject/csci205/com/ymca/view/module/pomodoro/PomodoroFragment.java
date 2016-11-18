package finalproject.csci205.com.ymca.view.module.pomodoro;

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

import finalproject.csci205.com.countdown.View.CountDownNotification;
import finalproject.csci205.com.countdown.View.CountDownView;
import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.presenter.module.PomodoroPresenter;



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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PomodoroPresenter pomodoroPresenter = new PomodoroPresenter(this);
        View root = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        countDownView = (CountDownView) root.findViewById(R.id.countDownViewInFragment);
        countDownView.setSessionTime(1);//TODO GET FROM MODEL -- > PRESENTER
        CountDownNotification notification = new CountDownNotification(getContext(), R.drawable.ic_pom);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 1, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setIntent(pendingIntent);
        notification.buildNotfication();
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
