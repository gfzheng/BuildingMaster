package cn.edu.sysu.buildingmaster.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.sysu.buildingmaster.Adapter.TaskItemRecyclerViewAdapter;
import cn.edu.sysu.buildingmaster.Fragment.dummy.TaskContent;
import cn.edu.sysu.buildingmaster.R;

public class MyTaskFragment extends Fragment {
    private static final String TAG = "MyTaskFragment";
    private TaskItemRecyclerViewAdapter adapter;
    public MyTaskFragment(TaskItemRecyclerViewAdapter adapter) {
        this.adapter =adapter;
    }

    public static MyTaskFragment newInstance(TaskItemRecyclerViewAdapter adapter) {
        return new MyTaskFragment(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_task_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}