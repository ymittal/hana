package finalproject.csci205.com.ymca.view.task.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import finalproject.csci205.com.ymca.R;
import finalproject.csci205.com.ymca.model.Subtask;
import finalproject.csci205.com.ymca.presenter.DetailTaskPresenter;

public class SubtasksAdapter extends RecyclerView.Adapter<SubtasksAdapter.SubtaskViewHolder> {

    /**
     * {@link DetailTaskPresenter} presenter
     */
    private final DetailTaskPresenter detailTaskPresenter;

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
    }

    /**
     * @return number of subtasks
     * @author Yash
     */
    @Override
    public int getItemCount() {
        return detailTaskPresenter.getNumSubtasks();
    }

    /**
     * A container class {@link RecyclerView.ViewHolder} to hold view elements for a subtask
     *
     * @author Yash
     */
    public class SubtaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

            btnCancel.setOnClickListener(this);
        }

        /**
         * Removes subtask when cancel button is clicked
         *
         * @param v one of recyclerview items
         */
        @Override
        public void onClick(View v) {
            if (v == btnCancel) {
                detailTaskPresenter.removeSubtask(getAdapterPosition());
            }
        }
    }
}
