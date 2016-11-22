package finalproject.csci205.com.ymca.view.module.pomodoro;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import finalproject.csci205.com.countdown.View.CountDownView;
import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.presenter.module.PomodoroPresenter;
import finalproject.csci205.com.ymca.view.MainActivity;


public class PomodoroFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private CountDownView countDownView;
    private ImageButton settingsBtn;

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

        final PomodoroPresenter pomodoroPresenter = new PomodoroPresenter(this);
        View root = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        countDownView = (CountDownView) root.findViewById(R.id.countDownViewInFragment);
        countDownView.setSessionTime(1);//TODO GET FROM MODEL -- > PRESENTER
        countDownView.setJumpTo(MainActivity.class);
        settingsBtn = (ImageButton) root.findViewById(R.id.settingsButtonYo);
        settingsBtn.setOnClickListener(this);

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
        if (view.getId() == settingsBtn.getId()) {
            FragmentManager fragmentManager = getFragmentManager();
            PomodoroSettingsDialogFragment pomodoroSettingsDialogFragment = new PomodoroSettingsDialogFragment();
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(R.id.content_nav, pomodoroSettingsDialogFragment)
                    .addToBackStack(null).commit();
            settingsBtn.setVisibility(View.GONE); //TODO Get view to come back, over-ride back btn
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
