package finalproject.csci205.com.ymca.view.task;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finalproject.csci205.com.ymca.R;

/**
 * Created by ym012 on 11/16/2016.
 */
public class DetailTaskFragment extends Fragment {

    public DetailTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        return root;
    }
}
