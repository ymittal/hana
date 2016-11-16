package finalproject.csci205.com.ymca.view.task.dialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.module.GTDPresenter;
import finalproject.csci205.com.ymca.view.task.GTDFragment;

/**
 * Created by ceh024 on 11/15/16.
 */

public class TaskFormFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    private EditText editText;
    private String passedTaskName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_gtd, container, false);
        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(this);

        initUI(root);

        return root;
    }

    private void initUI(View root) {
        Button save = (Button) root.findViewById(R.id.save);
        Button cancel = (Button) root.findViewById(R.id.cancel);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        editText = (EditText) root.findViewById(R.id.editText);
        editText.setText(passedTaskName);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                save();
                break;
            case R.id.cancel:
                returnToLast();
                break;
        }
    }

    /**
     * Reacts to the user hitting the save button
     */
    private void save() {
        GTDPresenter gtdPresenter = new GTDPresenter();
        gtdPresenter.addTask(new Task(editText.getText().toString(), false), false);
        returnToLast();
    }

    /**
     * Destroys this view, recreates and displays GTD View
     *
     * @author Charles
     */
    private void returnToLast() {
        Fragment fragment = GTDFragment.newInstance();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.content_nav, fragment).commit();
    }

    public void setTaskName(String passedTaskName) {
        this.passedTaskName = passedTaskName;
    }

    /**
     * Manual control of when user hits back button
     *
     * @param view
     * @param i
     * @param keyEvent
     * @return
     * @author Charles
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            returnToLast();
            return true;
        }

        return false;
    }
}
