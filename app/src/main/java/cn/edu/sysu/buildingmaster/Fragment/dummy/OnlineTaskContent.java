package cn.edu.sysu.buildingmaster.Fragment.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.sysu.buildingmaster.DataBase.TaskItem;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class OnlineTaskContent {
    public static final List<TaskItem> ITEMS = new ArrayList<TaskItem>();
    public static final Map<String, TaskItem> ITEM_MAP = new HashMap<String, TaskItem>();

    private static void addItem(TaskItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void initData(){
        AVQuery<AVObject> query = new AVQuery<>("Tasks");
        query.limit(10);
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<AVObject> avObjects) {
                for (AVObject o : avObjects){
                    TaskItem item = new TaskItem(o.getObjectId(),o.getString("title"),o.getString("description"),o.getInt("starCount"),o.getString("authorName"),false,o.getString("authorHead"),o.getString("detail"));
                    addItem(item);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
