package finalproject.csci205.com.ymca.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import finalproject.csci205.com.ymca.R;

/**
 * Created by Charles on 11/9/2016.
 */

public class GTDDialogFragment extends DialogFragment {

    public static final String NEW_TASK = "NEW_TASK";
    public static final String GTD_TASK = "GTD_TASK";

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_gtd, null);

        builder.setMessage("Add Task").setView(view);

        final EditText etGtdTask = (EditText) view.findViewById(R.id.etGtdTask);
        etGtdTask.setText(getArguments().getString(GTD_TASK));

        builder.setPositiveButton("Get Thing Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent()
                        .putExtra(NEW_TASK, etGtdTask.getText().toString());

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                dismiss();
            }
        });

        // create the AlertDialog object and return it
        return builder.create();
    }

}
