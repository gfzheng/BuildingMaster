package cn.edu.sysu.buildingmaster.Fragment.dummy;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.sysu.buildingmaster.Adapter.TaskItemRecyclerViewAdapter;
import cn.edu.sysu.buildingmaster.DataBase.TaskDatabase;
import cn.edu.sysu.buildingmaster.DataBase.TaskItem;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 本地任务数据数据源，存储在本地数据库中，App启动时读取
 */
public class TaskContent {
    private static final String TAG = "TaskContent";
    public static final List<TaskItem> ITEMS = new ArrayList<TaskItem>();
    public static final Map<String, TaskItem> ITEM_MAP = new HashMap<String, TaskItem>();

    public static void addItem(TaskItem item,Context context) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        TaskDatabase.getInstance(context).getDao().insertTask(item)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"Write task to database success.");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG,"Write task to database failed.");
                    }
                });
    }

    private static void addItems(List<TaskItem> items){
        ITEMS.addAll(items);
        for (TaskItem item : items){
            ITEM_MAP.put(item.id,item);
        }
    }

    public static void unStar(final String id, final TaskItemRecyclerViewAdapter adapter){
        TaskDatabase.getInstance(null).getDao().deleteTask(ITEM_MAP.get(id))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"Delete local data success.");
                        ITEMS.remove(ITEM_MAP.get(id));
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.i(TAG,"Delete local data failed.");
                    }
                });
    }

    public static void updateTask(TaskItem item, final Context context){
        TaskDatabase.getInstance(context).getDao().updateTask(item)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"update success.");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public static void initData(Context context){
        TaskDatabase.getInstance(context).getDao().getTasks()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<TaskItem>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<TaskItem> taskItems) {
                        addItems(taskItems);
                        Log.i(TAG,"Query local data success.");
                        Log.i(TAG,taskItems.size()+"");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG,"Query local data failed.");
                    }
                });
    }
}