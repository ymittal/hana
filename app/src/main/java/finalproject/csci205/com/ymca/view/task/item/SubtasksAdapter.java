package finalproject.csci205.com.ymca.view.task.item;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.presenter.module.DetailTaskPresenter;

public class SubtasksAdapter extends RecyclerView.Adapter<SubtaskViewHolder> {
    private DetailTaskPresenter detailTaskPresenter;

    public SubtasksAdapter(DetailTaskPresenter detailTaskPresenter) {
        this.detailTaskPresenter = detailTaskPresenter;
    }

    @Override
    public SubtaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_rv_item, parent, false);
        return new SubtaskViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final SubtaskViewHolder holder, final int position) {
        Subtask subtask = detailTaskPresenter.getSubtasks().get(position);
        holder.tvSubtask.setText(subtask.getTitle());
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LOG_TAG", detailTaskPresenter.getNumSubtasks() + " " + position + "");
                detailTaskPresenter.removeTask(position);
            }
        });

        holder.itemView.setTag(subtask);
    }

    @Override
    public int getItemCount() {
        return detailTaskPresenter.getNumSubtasks();
    }
}
