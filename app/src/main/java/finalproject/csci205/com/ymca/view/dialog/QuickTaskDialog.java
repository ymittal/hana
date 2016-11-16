package finalproject.csci205.com.ymca.view.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import finalproject.csci205.com.ymca.R;

/*
Note to Aleks. Reference the chapter in the busy coder's guide to android dev on this
AS well as ...
https://developer.android.com/guide/topics/ui/dialogs.html
*/

public class QuickTaskDialog extends DialogFragment {

    public static final String NEW_TASK = "NEW_TASK";
    public static final String GTD_TASK = "GTD_TASK";
    public static final int REQUEST_CODE_GTD = 2;

    public QuickTaskDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_quicktask, null);

        builder.setMessage("Add Task")
                .setView(view)
                .setPositiveButton("Get Thing Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Bundle bundle = new Bundle();
//                        EditText etAddTask = (EditText) view.findViewById(R.id.etAddTask);
//                        bundle.putString(GTD_TASK, etAddTask.getText().toString());
//
//                        GTDDialog gtdDialog = new GTDDialog();
//                        gtdDialog.setArguments(bundle);
//
//                        gtdDialog.setTargetFragment(getTargetFragment(), REQUEST_CODE_GTD);
//                        gtdDialog.show(getFragmentManager(), "Add GTD Task");
                        EditText etAddTask = (EditText) view.findViewById(R.id.etAddTask);
                        showDialog(etAddTask.getText().toString());

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

    public void showDialog(String s) {
        FragmentManager fragmentManager = getFragmentManager();
        TaskLongForm longForm = new TaskLongForm();
        longForm.setPassedTaskName(s);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.content_nav, longForm).commit();
    }
}
