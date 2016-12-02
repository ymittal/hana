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
import finalproject.csci205.com.ymca.presenter.GTDPresenter;
import finalproject.csci205.com.ymca.view.gesture.TaskItemTouchHelperCallback;
import finalproject.csci205.com.ymca.view.task.dialog.AddQuickTaskDialog;
import finalproject.csci205.com.ymca.view.task.item.SimpleDividerItemDecoration;

/**
 * A fragment to display a list of {@link Task}s added by a user. Activities containing
 * this fragment must implement {@link GTDFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 */
public class GTDFragment extends Fragment implements View.OnClickListener {

    /**
     * Constant to define request code for {@link AddQuickTaskDialog}'s result
     */
    public static final int REQUEST_CODE_QUICK = 1;
    /**
     * Name of {@link Task} title passed from {@link AddQuickTaskDialog}
     */
    public static final String NEW_TASK = "NEW_TASK";

    /**
     * {@link OnFragmentInteractionListener} to setup communication between current
     * fragment and container activities
     */
    private OnFragmentInteractionListener mListener;
    /**
     * Presenter for {@link GTDFragment}
     */
    private GTDPresenter gtdPresenter;

    /**
     * Required empty constructor
     */
    public GTDFragment() {
    }

    /**
     * Use this factory method to create a new instance of {@link GTDFragment}
     *
     * @return A new instance of {@link GTDFragment}
     * @author Charles
     */
    public static GTDFragment newInstance() {
        GTDFragment fragment = new GTDFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Sets up title of {@link GTDFragment}
     *
     * @param savedInstanceState
     * @author Charles and Yash
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Task List");
    }

    /**
     * Sets up fragment user interface and controls
     *
     * @param inflater           {@link LayoutInflater} to inflate views inside fragment
     * @param container          parent view encapsulating the fragment
     * @param savedInstanceState
     * @return view for the fragment interface
     * @author Yash and Charles
     */
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
     * Requests {@link finalproject.csci205.com.ymca.view.task.item.TasksAdapter} from
     * {@link GTDPresenter}, sets up view and gesture functionality associated with recyclerview
     * items
     *
     * @param root root view for the fragment interface
     * @author Charles and Yash
     */
    private void initTaskList(View root) {
        RecyclerView rvTasks = (RecyclerView) root.findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTasks.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        rvTasks.setAdapter(gtdPresenter.getTasksAdapter());

        // defines swipe to delete functionality
        ItemTouchHelper.Callback callback =
                new TaskItemTouchHelperCallback(gtdPresenter.getTasksAdapter(), rvTasks);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvTasks);
    }

    /**
     * Handles click events on views implementing {@link android.view.View.OnClickListener}
     *
     * @param view clicked view
     * @author Charles
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {

            // shows a dialog box to quickly add a task or extend the
            // dialog to a full task form
            AddQuickTaskDialog dialog = new AddQuickTaskDialog();
            dialog.setTargetFragment(GTDFragment.this, REQUEST_CODE_QUICK);
            dialog.show(getFragmentManager(), "Add Task");

        }
    }


    /**
     * Handles pending result when user interacts with a dialog spawned from the view,
     * stores task when user indicates such
     *
     * @param requestCode request code supplied by fragment
     * @param resultCode  result code supplied by {@link AddQuickTaskDialog}
     * @param data        {@link Intent} object containing {@link Task} data
     * @author Charles and Yash
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_QUICK:
                if (resultCode == Activity.RESULT_CANCELED) {
                    addNewTask(data);
                } else if (resultCode == Activity.RESULT_OK) {
                    Task newTask = addNewTask(data);
                    gtdPresenter.openDetailedTaskFragment(newTask);
                }
                break;
        }
    }

    /**
     * Adds new task through {@link GTDPresenter}
     *
     * @param data {@link Intent} object containing {@link Task} data
     * @return newly added {@link Task}
     * @author Charles and Yash
     */
    private Task addNewTask(Intent data) {
        String sNewTask = data.getExtras().getString(NEW_TASK);
        Task newTask = new Task(sNewTask);
        gtdPresenter.addTask(newTask, true);
        return newTask;
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     * @see <a href="http://developer.android.com/training/basics/fragments/communicating.html">
     * Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
