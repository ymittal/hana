package finalproject.csci205.com.ymca.view.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.model.item.TasksAdapter;
import finalproject.csci205.com.ymca.presenter.LifeCycle;
import finalproject.csci205.com.ymca.presenter.module.GTDPresenter;
import finalproject.csci205.com.ymca.view.Fab;
import finalproject.csci205.com.ymca.view.dialog.GTDDialogFragment;
import finalproject.csci205.com.ymca.view.dialog.QuickTaskDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GTDFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GTDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GTDFragment extends Fragment implements LifeCycle, View.OnClickListener {

    public static final int REQUEST_CODE_QUICK = 1;
    // TODO: fix static reference to presenter
    private static GTDPresenter GTDPRESENTER;

    private Fab fab;
    private View root;
    private MaterialSheetFab materialSheetFab;
    private TextView quickTask;
    private TextView addTask;

    private OnFragmentInteractionListener mListener;
    private TasksAdapter tasksAdapter;

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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and its features
        root = inflater.inflate(R.layout.fragment_gtd, container, false);
        fab = (Fab) root.findViewById(R.id.fab);
        quickTask = (TextView) root.findViewById(R.id.fab_sheet_item_quicktask);
        addTask = (TextView) root.findViewById(R.id.fab_sheet_item_task);
        quickTask.setOnClickListener(this);
        addTask.setOnClickListener(this);

        View overlay = root.findViewById(R.id.overlay);
        View sheetView = root.findViewById(R.id.fab_sheet);
        int sheetColor = getResources().getColor(R.color.white);
        int fabColor = getResources().getColor(R.color.colorPrimary);

        // Initialize material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);

        initTaskList(root);

        initSheetOnClickListener();

        return root;
    }

    private void initSheetOnClickListener() {
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Called when the material sheet's "show" animation starts.
            }

            @Override
            public void onSheetShown() {
                // Called when the material sheet's "show" animation ends.
            }

            @Override
            public void onHideSheet() {
                // Called when the material sheet's "hide" animation starts.
            }

            public void onSheetHidden() {
                // Called when the material sheet's "hide" animation ends.
            }
        });
    }


    private void initTaskList(View root) {
        RecyclerView rvTasks = (RecyclerView) root.findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));

        tasksAdapter = new TasksAdapter();
        rvTasks.setAdapter(tasksAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onClick(View view) {
        if (view.getId() == quickTask.getId()) {
            QuickTaskDialogFragment dialog = new QuickTaskDialogFragment();
            dialog.setTargetFragment(GTDFragment.this, REQUEST_CODE_QUICK);
            dialog.show(getFragmentManager(), "Add Task");

        } else if (view.getId() == addTask.getId()) {
            GTDDialogFragment dialog = new GTDDialogFragment();
            dialog.show(getFragmentManager(), "Add Task");

        }

        materialSheetFab.hideSheet();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_QUICK:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String sNewTask = bundle.getString(QuickTaskDialogFragment.NEW_TASK);
                    tasksAdapter.addItem(new Task(sNewTask, "Something", false));
                }
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
        // TODO: update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
