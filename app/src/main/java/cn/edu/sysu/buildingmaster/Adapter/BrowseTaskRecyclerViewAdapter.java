package cn.edu.sysu.buildingmaster.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.sysu.buildingmaster.DataBase.TaskItem;
import cn.edu.sysu.buildingmaster.Fragment.dummy.TaskContent;
import cn.edu.sysu.buildingmaster.Modules.NetModule;
import cn.edu.sysu.buildingmaster.R;

public class BrowseTaskRecyclerViewAdapter extends RecyclerView.Adapter<BrowseTaskRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "Adapter";
    private final List<TaskItem> mValues;
    private TaskItemRecyclerViewAdapter adapter;

    public BrowseTaskRecyclerViewAdapter(List<TaskItem> items,TaskItemRecyclerViewAdapter adapter) {
        Log.i(TAG,items.size()+"");
        mValues = items;
        this.adapter = adapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_my_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.i(TAG,mValues.get(position).toString());
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).title);
        holder.description.setText(mValues.get(position).description);
        if (TaskContent.ITEMS.size()== 0 || TaskContent.ITEM_MAP.get(mValues.get(position).id) == null){
            holder.stars.setImageResource(R.drawable.star_false);
        }else{
            holder.stars.setImageResource(R.drawable.star_true);
        }
        holder.star_count.setText(mValues.get(position).star_count+"");
        holder.perform.setVisibility(View.GONE);
        holder.stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TaskContent.ITEMS.size()== 0 || TaskContent.ITEM_MAP.get(mValues.get(position).id) == null){
                    mValues.get(position).star_count += 1;
                    TaskContent.addItem(mValues.get(position),holder.mView.getContext());
                    Toast.makeText(holder.mView.getContext(),"已将该任务添加至本地。",Toast.LENGTH_LONG).show();
                    holder.stars.setImageResource(R.drawable.star_true);
                    notifyItemChanged(position);
                    adapter.notifyDataSetChanged();
                    NetModule.changeStarCount(mValues.get(position).id,1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView description;
        public final ImageView stars;
        public final TextView star_count;
        public final Button perform;

        public TaskItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = view.findViewById(R.id.task_title);
            description = view.findViewById(R.id.task_description);
            stars = view.findViewById(R.id.task_stars);
            star_count = view.findViewById(R.id.task_star_count);
            perform = view.findViewById(R.id.task_perform);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + title + ": "+ description + "'";
        }
    }
}