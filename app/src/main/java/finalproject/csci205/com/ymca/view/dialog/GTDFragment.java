package finalproject.csci205.com.ymca.view.dialog;

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
import finalproject.csci205.com.ymca.view.module.gtd.TaskFragment;

/**
 * Created by ceh024 on 11/15/16.
 */

public class GTDFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {

    public static final String NEW_TASK = "NEW_TASK";
    public static final String GTD_TASK = "GTD_TASK";

    private Button save;
    private Button cancel;
    private EditText editText;
    private String passedTaskName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialogform_layout, container, false);
        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(this);

        initUI(root);

        return root;
    }

    private void initUI(View root) {
        save = (Button) root.findViewById(R.id.save);
        save.setOnClickListener(this);
        cancel = (Button) root.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        editText = (EditText) root.findViewById(R.id.editText);
        editText.setText(passedTaskName);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == save.getId()) {
            save();
        } else if (view.getId() == cancel.getId()) {
            returnToLast();
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
        Fragment fragment = TaskFragment.newInstance();

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
