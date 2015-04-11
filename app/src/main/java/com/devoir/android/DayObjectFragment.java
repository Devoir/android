package com.devoir.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Brady on 3/13/2015.
 */
public class DayObjectFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        Bundle args = getArguments();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.task_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerDecoration(getActivity(), null));

        taskAdapter = new TaskAdapter(getActivity(), args);
        taskAdapter.setOnItemClickListener((TaskActivity) getActivity());
        taskAdapter.setOnLongClick((TaskActivity) getActivity());
        recyclerView.setAdapter(taskAdapter);

        ((TaskActivity) getActivity()).setFragment(this);

        //((TextView) rootView.findViewById(android.R.id.text1)).setText(Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }

    public TaskAdapter getTaskAdapter() {
        return this.taskAdapter;
    }

    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }
}
