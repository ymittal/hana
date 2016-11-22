package finalproject.csci205.com.ymca.view.task.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import finalproject.csci205.com.ymca.R;


public class DatePickerDialog extends DialogFragment {
    private DatePicker datePicker;


    /**
     * Required empty public constructor
     */
    public DatePickerDialog() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

        Toast.makeText(getContext(), "It's passed!", Toast.LENGTH_SHORT);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_date_picker_dialog, null);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Due Date")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .create();

        addOnClickListenersToButtons(view, dialog);

        // opens soft keyboard
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }

    /**
     * Adds {@link View.OnClickListener} to dialog buttons shown on {@link AlertDialog}
     *
     * @param view   The view in which the buttons are located
     * @param dialog The dialog in which the buttons are located
     * @author Malachi
     */
    private void addOnClickListenersToButtons(View view, final AlertDialog dialog) {

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                //goToGTDFragment(etAddTask.getText().toString(), Activity.RESULT_OK);
                                Intent i = new Intent()
                                        .putExtra("dayOfMonth", datePicker.getDayOfMonth())
                                        .putExtra("month", datePicker.getMonth())
                                        .putExtra("year", datePicker.getYear());
                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                                dismiss();
                                //checking input
//                                    Toast toast = null;
//                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                                        toast = Toast.makeText(getContext(), "YAY", Toast.LENGTH_LONG);
//                                    }
//                                    toast.show();

                            }
                        });

                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // goToGTDFragment(etAddTask.getText().toString(), Activity.RESULT_CANCELED);

                                //checking input
                                Toast toast = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    toast = Toast.makeText(getContext(), "YAY2", Toast.LENGTH_LONG);
                                }
                                toast.show();

                            }
                        });
            }
        });
    }

}


