package finalproject.csci205.com.ymca.view.task.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import finalproject.csci205.com.ymca.R;

public class AddQuickTaskDialog extends DialogFragment {

    public static final String NEW_TASK = "NEW_TASK";

    public AddQuickTaskDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_quicktask, null);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Add Task")
                .setPositiveButton("Edit", null)
                .setNegativeButton("Save", null)
                .create();

        addOnClickListenersToButtons(view, dialog);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    private void addOnClickListenersToButtons(View view, final AlertDialog dialog) {
        final EditText etAddTask = (EditText) view.findViewById(R.id.etAddTask);
        final TextInputLayout til = (TextInputLayout) view.findViewById(R.id.tilAddTask);
        til.setErrorEnabled(true);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (etAddTask.getText().toString().equals("")) {
                                    til.setError("Enter a task name");
                                } else {
                                    showTaskFormDialog(etAddTask.getText().toString());
                                }
                            }
                        });

                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (etAddTask.getText().toString().equals("")) {
                                    til.setError("Enter a task name");
                                } else {
                                    goToGTDFragment(etAddTask.getText().toString());
                                }
                            }
                        });
            }
        });
    }

    private void goToGTDFragment(String sTask) {
        Intent i = new Intent().putExtra(NEW_TASK, sTask);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
        dismiss();
    }

    public void showTaskFormDialog(String sTask) {
        TaskFormFragment taskFormFragment = new TaskFormFragment();
        taskFormFragment.setTaskName(sTask);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.content_nav, taskFormFragment).commit();
        dismiss();
    }
}
