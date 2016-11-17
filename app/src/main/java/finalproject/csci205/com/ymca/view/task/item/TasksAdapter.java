package finalproject.csci205.com.ymca.view.task.item;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.module.GTDPresenter;
import finalproject.csci205.com.ymca.view.gesture.TaskTouchHelperAdapter;

public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> implements TaskTouchHelperAdapter {
    private GTDPresenter gtdPresenter;
    private Context context;

    public TasksAdapter(GTDPresenter gtdPresenter) {
        this.gtdPresenter = gtdPresenter;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_rv_item, parent, false);
        return new TaskViewHolder(rowView);
    }

    /**
     * Updates the view if user checks a Task. State is updated in the Model afterwards.
     *
     * @param holder
     * @param position
     * @author Charles and Yash
     */
    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        final Task task = gtdPresenter.getTasks().get(position);
        holder.tvTask.setText(task.getTitle());
        holder.checkboxTask.setChecked(task.isComplete());
        holder.checkboxTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isComplete) {
                gtdPresenter.taskChecked(holder.getAdapterPosition(), isComplete);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gtdPresenter.openDetailedTaskFragment(task);
            }
        });

        holder.itemView.setTag(task);
    }

    @Override
    public int getItemCount() {
        return gtdPresenter.taskSize();
    }

    /**
     * Notifies Presenter that the Task is to be removed, allows user to undo action if so.
     *
     * @param position
     * @param recyclerView
     * @author Charles and Yash
     */
    @Override
    public void onItemDismiss(final int position, RecyclerView recyclerView) {
        gtdPresenter.removeTask(position);

        Snackbar snackbar = Snackbar
                .make(recyclerView, "Item has been deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gtdPresenter.restoreTask(position);
                    }
                });
        snackbar.show();
        //TODO Hide FAB during this.
    }
}
