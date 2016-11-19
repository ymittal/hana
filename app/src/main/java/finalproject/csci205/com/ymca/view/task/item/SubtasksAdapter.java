package finalproject.csci205.com.ymca.view.task.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.presenter.module.DetailTaskPresenter;

public class SubtasksAdapter extends RecyclerView.Adapter<SubtaskViewHolder> {

    /**
     * {@link DetailTaskPresenter} presenter
     */
    private DetailTaskPresenter detailTaskPresenter;

    /**
     * @param detailTaskPresenter {@link DetailTaskPresenter} presenter
     * @author Yash
     */
    public SubtasksAdapter(DetailTaskPresenter detailTaskPresenter) {
        this.detailTaskPresenter = detailTaskPresenter;
    }

    /**
     * Creates a {@link SubtaskViewHolder} using layout for each recyclerview item
     *
     * @param parent   parent view of recyclerview
     * @param viewType
     * @return
     * @author Yash
     */
    @Override
    public SubtaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_rv_item, parent, false);
        return new SubtaskViewHolder(rowView);
    }

    /**
     * Binds view to data, updates view if user deletes a task
     *
     * @param holder   container to hold view elements
     * @param position position of item in recyclerview
     * @author Yash
     */
    @Override
    public void onBindViewHolder(final SubtaskViewHolder holder, final int position) {
        Subtask subtask = detailTaskPresenter.getSubtasks().get(position);
        holder.tvSubtask.setText(subtask.getTitle());
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailTaskPresenter.removeTask(position);
            }
        });

        holder.itemView.setTag(subtask);
    }

    /**
     * @return number of subtasks
     * @author Yash
     */
    @Override
    public int getItemCount() {
        return detailTaskPresenter.getNumSubtasks();
    }
}
