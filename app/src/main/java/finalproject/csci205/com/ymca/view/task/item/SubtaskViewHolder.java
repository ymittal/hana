package finalproject.csci205.com.ymca.view.task.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import finalproject.csci205.com.ymca.R;

/**
 * A container class {@link RecyclerView.ViewHolder} to hold view elements for a subtask
 * @author Yash
 */
public class SubtaskViewHolder extends RecyclerView.ViewHolder {

    /**
     * {@link TextView} for subtask title
     */
    public final TextView tvSubtask;
    /**
     * {@link ImageView} for a button to delete subtask
     */
    public final ImageView btnCancel;

    /**
     * @param itemView view containing subtask view elements
     */
    public SubtaskViewHolder(View itemView) {
        super(itemView);
        tvSubtask = (TextView) itemView.findViewById(R.id.tvSubtask);
        btnCancel = (ImageView) itemView.findViewById(R.id.btnCancel);
    }

}