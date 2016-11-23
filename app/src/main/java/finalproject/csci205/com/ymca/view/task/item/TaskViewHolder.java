package finalproject.csci205.com.ymca.view.task.item;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import finalproject.csci205.com.ymca.R;

/**
 * A container class {@link RecyclerView.ViewHolder} to hold view elements for a task
 *
 * @author Yash
 * @see <a href="https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView">
 * GitHub - Using the RecyclerView</a>
 */
public class TaskViewHolder extends RecyclerView.ViewHolder {

    /**
     * {@link TextView} for task title
     */
    public final TextView tvTask;
    /**
     * {@link CheckBox} for whether task has been completed or not
     */
    public final CheckBox checkboxTask;
    /**
     * {@link TextView} for time remaining
     */
    public final TextView tvTimeLeft;

    /**
     * @param itemView view containing task view elements
     */
    public TaskViewHolder(View itemView) {
        super(itemView);
        tvTask = (TextView) itemView.findViewById(R.id.tvTask);
        checkboxTask = (CheckBox) itemView.findViewById(R.id.checkboxTask);
        tvTimeLeft = (TextView) itemView.findViewById(R.id.tvTimeLeft);
    }
}
