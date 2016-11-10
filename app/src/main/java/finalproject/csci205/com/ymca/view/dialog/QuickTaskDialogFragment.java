package finalproject.csci205.com.ymca.view.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import finalproject.csci205.com.ymca.R;

/*
Note to Aleks. Reference the chapter in the busy coder's guide to android dev on this
AS well as ...
https://developer.android.com/guide/topics/ui/dialogs.html
*/

public class QuickTaskDialogFragment extends DialogFragment {

    public static final String NEW_TASK = "NEW_TASK";
    public static final int REQUEST_CODE_GTD = 2;

    public QuickTaskDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_quicktask_dialog, null);

        builder.setMessage("Add Task")
                .setView(view)
                .setPositiveButton("Right Now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText etAddTask = (EditText) view.findViewById(R.id.etAddTask);
                        GTDDialogFragment gtdDialog = new GTDDialogFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("TASK", etAddTask.getText().toString());
                        gtdDialog.setArguments(bundle);

                        gtdDialog.setTargetFragment(getTargetFragment(), REQUEST_CODE_GTD);
                        gtdDialog.show(getFragmentManager(), "Add GTD Task");
                    }
                })
                .setNegativeButton("Save for Later", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText etAddTask = (EditText) view.findViewById(R.id.etAddTask);
                        Intent i = new Intent()
                                .putExtra(NEW_TASK, etAddTask.getText().toString());

                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                        dismiss();
                    }
                });

        // create the AlertDialog object and return it
        return builder.create();
    }
}
