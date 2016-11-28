package finalproject.csci205.com.ymca.view.task.item;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Task;
import finalproject.csci205.com.ymca.presenter.GTDPresenter;
import finalproject.csci205.com.ymca.util.DateTimeUtil;
import finalproject.csci205.com.ymca.view.gesture.TaskTouchHelperAdapter;

/**
 * An adapter class {@link RecyclerView.Adapter} to bind task list with view
 *
 * @see <a href="https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView">
 * GitHub - Using the RecyclerView</a>
 */
public class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> implements TaskTouchHelperAdapter {

    /**
     * {@link GTDPresenter} presenter
     */
    private GTDPresenter gtdPresenter;

    /**
     * @param gtdPresenter {@link GTDPresenter} presenter
     * @author Charles
     */
    public TasksAdapter(GTDPresenter gtdPresenter) {
        this.gtdPresenter = gtdPresenter;
    }

    /**
     * Creates a {@link TaskViewHolder} using layout for each recyclerview item
     *
     * @param parent   parent view of recyclerview
     * @param viewType
     * @return {@link TaskViewHolder} object
     * @author Yash
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_rv_item, parent, false);
        return new TaskViewHolder(rowView);
    }

    /**
     * Binds view to data, updates view if user checks a task, state is updated in the model afterwards.
     *
     * @param holder   container to hold view elements
     * @param position position of item in recyclerview
     * @author Charles and Yash
     */
    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        final Task task = gtdPresenter.getTasks().get(position);

        holder.tvTask.setText(task.getTitle());
        holder.checkboxTask.setChecked(task.isComplete());

        // sets time remaining if task due date has been defined by user
        if (task.getDueDate() == null) {
            holder.tvTimeLeft.setText("Tap to edit details");
        } else {
            holder.tvTimeLeft.setText(DateTimeUtil.convertDateToTimeRemaining(task.getDueDate()));
        }

        // updates task completion status in Database
        holder.checkboxTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isComplete) {
                gtdPresenter.taskChecked(holder.getAdapterPosition(), isComplete);
            }
        });

        // opens DetailTaskFragment when recyclervier item is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gtdPresenter.openDetailedTaskFragment(task);
            }
        });

        holder.itemView.setTag(task);
    }

    /**
     * @return number of tasks
     * @author Yash
     */
    @Override
    public int getItemCount() {
        return gtdPresenter.taskSize();
    }

    /**
     * Notifies presenter that a task is to be removed, allows user to undo action if so.
     *
     * @param position     position of item in recyclerview
     * @param recyclerView recyclerview
     * @author Charles and Yash
     */
    @Override
    public void onItemDismiss(final int position, RecyclerView recyclerView) {
        gtdPresenter.removeTask(position);

        // allows user to undo task delete
        Snackbar snackbar = Snackbar
                .make(recyclerView, "Item has been deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gtdPresenter.restoreTask(position);
                    }
                });
        snackbar.show();
    }
}
