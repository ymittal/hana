//package finalproject.csci205.com.ymca.view.util;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.support.design.widget.TextInputLayout;
//import android.support.v7.app.AlertDialog;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//
//import finalproject.csci205.com.ymca.R;
//
///**
// * Created by Malachi on 11/21/2016.
// */
//
//public class DialogUtil {
//
//    /**
//     * Sets up and opens {@link AlertDialog} and opens soft keyboard on start
//     *
//     * @param view The view which contains the dialog
//     * @param title The title of the dialog
//     * @param positiveButtonName The title of the button located on the left
//     * @param negativeButtonName THe title of the button located on the right
//     * @return dialog The created dialog
//     * @author Charles and Malachi
//     */
//    public static Dialog createDialog(View view, String title, String positiveButtonName, String negativeButtonName){
//
//
//        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
//                .setView(view)
//                .setTitle(title)
//                .setPositiveButton(positiveButtonName, null)
//                .setNegativeButton(negativeButtonName, null)
//                .create();
//
//        addOnClickListenersToButtons(view, dialog);
//
//        // opens soft keyboard
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        return dialog;
//    }
//
//    /**
//     * Adds {@link View.OnClickListener} to dialog buttons shown on {@link AlertDialog}
//     *
//     * @param view   view
//     * @param dialog alert dialog
//     * @author Yash and Malachi
//     */
//    private static void addOnClickListenersToButtons(View view, final AlertDialog dialog) {
//        final EditText etAddTask = (EditText) view.findViewById(R.id.etAddTask);
//        final TextInputLayout til = (TextInputLayout) view.findViewById(R.id.tilAddTask);
//
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//                        .setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (etAddTask.getText().toString().equals("")) {
//                                    til.setError("Enter a task name");
//                                } else {
//                                    goToGTDFragment(etAddTask.getText().toString(), Activity.RESULT_OK);
//                                }
//                            }
//                        });
//
//                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
//                        .setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (etAddTask.getText().toString().equals("")) {
//                                    til.setError("Enter a task name");
//                                } else {
//                                    goToGTDFragment(etAddTask.getText().toString(), Activity.RESULT_CANCELED);
//                                }
//                            }
//                        });
//            }
//        });
//    }
//
//}
