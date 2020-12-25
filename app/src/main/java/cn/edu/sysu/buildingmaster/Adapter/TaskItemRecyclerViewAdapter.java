package cn.edu.sysu.buildingmaster.Adapter;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import cn.edu.sysu.buildingmaster.Bean.EventState;
import cn.edu.sysu.buildingmaster.DataBase.TaskItem;
import cn.edu.sysu.buildingmaster.Fragment.dummy.OnlineTaskContent;
import cn.edu.sysu.buildingmaster.Fragment.dummy.TaskContent;
import cn.edu.sysu.buildingmaster.Modules.NetModule;
import cn.edu.sysu.buildingmaster.Util.CheckService;
import cn.edu.sysu.buildingmaster.R;

import java.util.List;

public class TaskItemRecyclerViewAdapter extends RecyclerView.Adapter<TaskItemRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "Adapter";
    private final List<TaskItem> mValues;
    private BrowseTaskRecyclerViewAdapter adapter;

    public TaskItemRecyclerViewAdapter(List<TaskItem> items) {
        mValues = items;
    }

    public void setRelateAdapter(BrowseTaskRecyclerViewAdapter adapter){
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
        holder.star_count.setVisibility(View.GONE);
        holder.perform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (CheckService.checkIsOn(holder.mView.getContext())){
                EventBus.getDefault().post(new EventState(mValues.get(position).id,mValues.get(position).title,mValues.get(position).info));
            }
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mView.getContext());
                builder.setTitle("删除任务");
                builder.setMessage("确定要删除该任务吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OnlineTaskContent.ITEM_MAP.get(mValues.get(position).id).star_count -= 1;
                        NetModule.changeStarCount(mValues.get(position).id,-1);
                        TaskContent.unStar(mValues.get(position).id,TaskItemRecyclerViewAdapter.this);
                        Toast.makeText(holder.mView.getContext(),"已删除该任务。",Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
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