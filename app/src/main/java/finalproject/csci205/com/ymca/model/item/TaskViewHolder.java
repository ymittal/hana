package finalproject.csci205.com.ymca.model.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import finalproject.csci205.com.ymca.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    public final TextView tvTask;
    public final CheckBox checkboxTask;

    public TaskViewHolder(View itemView) {
        super(itemView);
        tvTask = (TextView) itemView.findViewById(R.id.tvTask);
        checkboxTask = (CheckBox) itemView.findViewById(R.id.checkboxTask);
    }

}
