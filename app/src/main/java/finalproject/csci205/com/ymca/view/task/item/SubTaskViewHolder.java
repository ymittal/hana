package finalproject.csci205.com.ymca.view.task.item;

import android.icu.text.CollationElementIterator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import finalproject.csci205.com.ymca.R;

public class SubtaskViewHolder extends RecyclerView.ViewHolder {


    public final TextView tvSubtask;

    public SubtaskViewHolder(View itemView) {
        super(itemView);
        tvSubtask = (TextView) itemView.findViewById(R.id.tvSubtask);
    }

}