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

import finalproject.csci205.com.countdown.Ults.ServiceState;
import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Pom.PomodoroSettings;
import finalproject.csci205.com.ymca.presenter.PomodoroPresenter;
import finalproject.csci205.com.ymca.util.Constants;
import finalproject.csci205.com.ymca.view.MainActivity;


public class PomodoroFragment extends Fragment implements View.OnClickListener, OnBackStackListener {

    private OnFragmentInteractionListener mListener;
    private CountDownView countDownView;
    private ImageButton settingsBtn;
    private PomodoroPresenter pomodoroPresenter;

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
        pomodoroPresenter = new PomodoroPresenter(this);
        PomodoroSettings pomodoroSettings = pomodoroPresenter.getSavedPomSettings();
        View root = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        countDownView = (CountDownView) root.findViewById(R.id.countDownViewInFragment);

        if (pomodoroSettings != null) {
            countDownView.setSessionTime(pomodoroSettings.getSessionTime());
        } else {

            countDownView.setSessionTime(0); //Temp Config!
        }
        countDownView.setJumpTo(MainActivity.class);
        settingsBtn = (ImageButton) root.findViewById(R.id.btnPomodoroSettings);
        settingsBtn.setOnClickListener(this);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownView.getCd().getState() == ServiceState.OTHER) {
            Constants.destroyPomNotification(getContext());
        }
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
            showSettings();
        }
    }

    /**
     * Overlays a new fragment to gather settings
     *
     * @author Charles
     */
    private void showSettings() {
        FragmentManager fragmentManager = getFragmentManager();
        PomodoroSettingsFragment pomodoroSettingsDialogFragment = new PomodoroSettingsFragment();
        pomodoroSettingsDialogFragment.setOnBackStackListener(this);
        pomodoroSettingsDialogFragment.setCdRef(countDownView);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.content_nav, pomodoroSettingsDialogFragment)
                .addToBackStack(null).commit();
        settingsBtn.setVisibility(View.GONE);
    }

    /**
     * OnBackStackListener
     *
     * @author Charles
     */
    @Override
    public void onViewReturn() {
        settingsBtn.setVisibility(View.VISIBLE);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
