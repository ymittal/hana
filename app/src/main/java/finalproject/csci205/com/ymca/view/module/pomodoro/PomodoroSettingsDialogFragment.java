package finalproject.csci205.com.ymca.view.module.pomodoro;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import finalproject.csci205.com.ymca.R;

/**
 * Created by ceh024 on 11/21/16.
 */

public class PomodoroSettingsDialogFragment extends DialogFragment implements View.OnClickListener {

    private View root;
    private EditText sessionTime;
    private EditText breakTime;
    private EditText numBreaks;
    private EditText longBreak;
    private ImageButton saveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.pomodorosettings_dialog, container, false);
        sessionTime = (EditText) root.findViewById(R.id.sessionTime);
        breakTime = (EditText) root.findViewById(R.id.breakTime);
        numBreaks = (EditText) root.findViewById(R.id.numBreaks);
        longBreak = (EditText) root.findViewById(R.id.longBreak);
        saveBtn = (ImageButton) root.findViewById(R.id.saveBtn);
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == saveBtn.getId()) {
            Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
        }
    }
}
