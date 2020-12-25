package cn.edu.sysu.buildingmaster.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.sysu.buildingmaster.Adapter.BrowseTaskRecyclerViewAdapter;
import cn.edu.sysu.buildingmaster.Adapter.TaskItemRecyclerViewAdapter;
import cn.edu.sysu.buildingmaster.Fragment.dummy.OnlineTaskContent;
import cn.edu.sysu.buildingmaster.Fragment.dummy.TaskContent;
import cn.edu.sysu.buildingmaster.R;

public class BrowseFragment extends Fragment {
    private BrowseTaskRecyclerViewAdapter adapter;
    public BrowseFragment(BrowseTaskRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public static BrowseFragment newInstance(BrowseTaskRecyclerViewAdapter adapter) {
        return new BrowseFragment(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}