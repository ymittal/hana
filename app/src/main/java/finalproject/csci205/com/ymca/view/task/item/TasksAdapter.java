package finalproject.csci205.com.ymca.view.task.item;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
     * Alpha of {@link TaskViewHolder#tvTimeLeft} when {@link Task} is done
     */
    private static final float ALPHA_TASK_DONE = 0.25f;
    /**
     * Alpha of {@link TaskViewHolder#tvTimeLeft} when {@link Task} is not done
     */
    private static final float ALPHA_TASK_UNDONE = 1.0f;

    /**
     * Holds the view {@link Context}
     */
    private final Context context;
    /**
     * {@link GTDPresenter} presenter
     */
    private final GTDPresenter gtdPresenter;

    /**
     * @param gtdPresenter {@link GTDPresenter} presenter
     * @param context      view {@link Context}
     * @author Charles
     */
    public TasksAdapter(GTDPresenter gtdPresenter, Context context) {
        this.gtdPresenter = gtdPresenter;
        this.context = context;
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
        setViewHolderUI(holder, task);

        // updates task completion status in Database
        holder.checkboxTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean changeTo = !task.isComplete();
                gtdPresenter.taskChecked(holder.getAdapterPosition(), changeTo);

                setViewHolderUI(holder, task);
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
     * Initializes the UI elements for a {@link TaskViewHolder} and designs it according to
     * whether the {@link Task} has been completed or its due date has been set
     *
     * @param holder {@link TaskViewHolder} object
     * @param task   {@link Task} represented by view
     */
    private void setViewHolderUI(TaskViewHolder holder, Task task) {
        holder.tvTask.setText(task.getTitle());
        holder.checkboxTask.setChecked(task.isComplete());

        if (task.isComplete()) {
            holder.itemView.setAlpha(ALPHA_TASK_DONE);
            holder.tvTimeLeft.setVisibility(View.GONE);
        } else {
            holder.itemView.setAlpha(ALPHA_TASK_UNDONE);
            holder.tvTimeLeft.setVisibility(View.VISIBLE);

            // sets time remaining if task due date has been defined by user
            if (task.getDueDate() != null) {
                holder.tvTimeLeft.setText(DateTimeUtil.convertDateToTimeRemaining(task.getDueDate()));
                holder.tvTimeLeft.setTextColor(Color.WHITE);
            } else {
                holder.tvTimeLeft.setText(R.string.tv_time_left_unset);
                holder.tvTimeLeft.setTextColor(
                        ContextCompat.getColor(context, R.color.tv_time_left_unset));
            }
        }
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
                .make(recyclerView, R.string.snackbar_task_delete, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_task_undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gtdPresenter.restoreTask(position);
                    }
                });
        snackbar.show();
    }
}
