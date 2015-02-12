package com.devoir.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.devoir.android.Database.DBContract;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Adapters are responsible for accessing items in my dataset. It is the DAO.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> dataSet;
    private AdapterView.OnItemClickListener onItemClickListener;

    public TaskAdapter(Context context) {
        this.dataSet = DBContract.loadTasks(context);

        Collections.sort(dataSet, Task.NameComparator);
        //Collections.sort(dataSet);
        notifyDataSetChanged();
    }

    /*
    * Inserting a new item at the head of the list. This uses a specialized
    * RecyclerView method, notifyItemInserted(), to trigger any enabled item
    * animations in addition to updating the view.
    */
    public void addTask(Context context, Task task) {
        dataSet.add(0, task);
        //Collections.sort(dataSet);
        notifyItemInserted(0);
        //DBContract.addTask(context, task);
    }

    public void removeTask(int index) {
        if (!dataSet.isEmpty()) {
            dataSet.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void search(String query) {
        // TODO implmement a real search -- this just returns all tasks.
        ArrayList<Task> newDataSet = new ArrayList<Task>();
        for (Task f : dataSet) {
            newDataSet.add(f);
        }
        this.dataSet = newDataSet;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_task_list_item, parent, false);
        return new ViewHolder(v, this);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Task task = dataSet.get(position);
        holder.setName(task.getName());
        holder.setDescription(task.getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(ViewHolder holder) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, holder.itemView, holder.getPosition(), holder.getItemId());
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView mName;
        private TextView mDescription;

        private TaskAdapter mAdapter;

        public ViewHolder(View v, TaskAdapter adapter) {
            super(v);
            v.setOnClickListener(this);

            mAdapter = adapter;

            mName = (TextView) v.findViewById(R.id.task_name);
            mDescription = (TextView) v.findViewById(R.id.task_description);
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }

        public void setName(String name) {
            mName.setText(name);
        }

        public void setDescription(String description) {
            mDescription.setText(description);
        }
    }
}