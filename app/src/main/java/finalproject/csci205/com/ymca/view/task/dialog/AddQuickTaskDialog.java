package finalproject.csci205.com.ymca.view.task.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import finalproject.csci205.com.ymca.R;

/**
 * {@link DialogFragment} to have a user enter a task quickly or move to
 * {@link finalproject.csci205.com.ymca.view.task.DetailTaskFragment}
 */
public class AddQuickTaskDialog extends DialogFragment {

    public static final String NEW_TASK = "NEW_TASK";

    /**
     * Required empty constructor
     */
    public AddQuickTaskDialog() {
    }

    /**
     * Sets up {@link AlertDialog} and opens soft keyboard on start
     *
     * @param savedInstanceState
     * @return dialog
     * @author Charles
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_quicktask, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Add Task")
                .setPositiveButton(R.string.positive_btn_edit, null)
                .setNegativeButton(R.string.negative_btn_save, null)
                .create();

        addOnClickListenersToButtons(view, dialog);

        // opens soft keyboard
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    /**
     * Adds {@link View.OnClickListener} to dialog buttons shown on {@link AlertDialog}
     *
     * @param view   view
     * @param dialog alert dialog
     * @author YMCA
     */
    private void addOnClickListenersToButtons(View view, final AlertDialog dialog) {
        final EditText etAddTask = (EditText) view.findViewById(R.id.etAddTask);
        final TextInputLayout til = (TextInputLayout) view.findViewById(R.id.tilAddTask);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (etAddTask.getText().toString().equals("")) {
                                    til.setError(getString(R.string.error_til_add_task));
                                } else {
                                    goToGTDFragment(etAddTask.getText().toString(), Activity.RESULT_OK);
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
                                    goToGTDFragment(etAddTask.getText().toString(), Activity.RESULT_CANCELED);
                                }
                            }
                        });
            }
        });
    }

    /**
     * Replaces current fragment to the fragment containing the task list
     * {@link finalproject.csci205.com.ymca.view.task.GTDFragment}
     *
     * @param sTask      title of task to be saved
     * @param resultType result type <code>Activity.RESULT_CANCELED</code> or <code>Activity.RESULT_OK</code>
     * @author Charles
     */
    private void goToGTDFragment(String sTask, int resultType) {
        Intent i = new Intent().putExtra(NEW_TASK, sTask);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultType, i);
        dismiss();
    }
}
