package finalproject.csci205.com.ymca.view.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;

/**
 * Created by ym012 on 11/16/2016.
 */
public class DetailTaskFragment extends Fragment implements View.OnKeyListener {

    public static final String SERIALIZED_TASK = "SERIALIZED_TASK";

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
        root.setOnKeyListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            task = (Task) bundle.getSerializable(SERIALIZED_TASK);
            getActivity().setTitle(task.getTitle());
        }

        return root;
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            goToGTDFragment();
            return true;
        }

        return false;
    }

    private void goToGTDFragment() {
        Fragment fragment = GTDFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_nav, fragment).commit();
    }
}
