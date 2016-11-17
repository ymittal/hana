package finalproject.csci205.com.ymca.view.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.module.GTDPresenter;
import finalproject.csci205.com.ymca.view.gesture.TaskItemTouchTouchHelperCallback;
import finalproject.csci205.com.ymca.view.task.dialog.AddQuickTaskDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GTDFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GTDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GTDFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_CODE_QUICK = 1;
    public static final String NEW_TASK = "NEW_TASK";

    private OnFragmentInteractionListener mListener;
    private GTDPresenter gtdPresenter;

    public GTDFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GTDFragment.
     */
    public static GTDFragment newInstance() {
        GTDFragment fragment = new GTDFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Task List");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gtd, container, false);
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        gtdPresenter = new GTDPresenter(this);

        initTaskList(root);
        return root;
    }


    /**
     * Requests the TaskAdapter from Presenter, set's up view and corresponding
     * functionality that occurs with user interaction.
     *
     * @param root
     * @author Charles and Yash
     */
    private void initTaskList(View root) {
        RecyclerView rvTasks = (RecyclerView) root.findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTasks.setAdapter(gtdPresenter.getTasksAdapter());

        // defines swipe to delete functionality
        ItemTouchHelper.Callback callback =
                new TaskItemTouchTouchHelperCallback(gtdPresenter.getTasksAdapter(), rvTasks);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvTasks);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Handles view actions between the user, and the defined response depending on the
     * object defined to the OnClickListener.
     *
     * @param view
     * @author Charles
     */
    @Override
    public void onClick(View view) {
        /*
           Responses with a dialog box that gives the user a choice of a quick task,
           or the option to extend it to the full task form.

           This fragment will handle the transition to display the dialog box.
         */
        if (view.getId() == R.id.fab) {
            AddQuickTaskDialog dialog = new AddQuickTaskDialog();
            dialog.setTargetFragment(GTDFragment.this, REQUEST_CODE_QUICK);
            dialog.show(getFragmentManager(), "Add Task");

        }
    }


    /**
     * Handle's the pending result when user interacts with a dialog spawned from the view.
     * Stores task when user indicates such.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @author Charles and Yash
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_QUICK:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String sNewTask = bundle.getString(NEW_TASK);
                    gtdPresenter.addTask(new Task(sNewTask, false), true);
                }
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
