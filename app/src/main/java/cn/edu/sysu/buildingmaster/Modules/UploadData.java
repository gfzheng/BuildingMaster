package cn.edu.sysu.buildingmaster.Modules;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;

import cn.edu.sysu.buildingmaster.DataBase.TaskDatabase;
import cn.edu.sysu.buildingmaster.DataBase.TaskItem;
import cn.edu.sysu.buildingmaster.Fragment.dummy.TaskContent;
import cn.edu.sysu.buildingmaster.R;
import cn.edu.sysu.buildingmaster.Task.TmallFarm;
import cn.edu.sysu.buildingmaster.Task.Wechat;
import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UploadData {
    private static final String TAG = "InitData";
    public static void initData(Context context){
        ArrayList<TaskItem> items =new ArrayList<>();
        TaskItem tmallFarm = new TaskItem("1594796172267","天猫农场刷阳光","自动浏览商品网页，从而获取阳光。",0,"Roger",false, BitmapFactory.decodeResource(context.getResources(), R.drawable.head), TmallFarm.collectSunshine());
        TaskItem shareMoment = new TaskItem("0","自动发送朋友圈","可以自动发送一条测试朋友圈",0,"Roger",false,BitmapFactory.decodeResource(context.getResources(), R.drawable.head), Wechat.shareMoment());
        items.add(tmallFarm);
        items.add(shareMoment);
        transformDataAndUpload(items);
    }

    public static void transformDataAndUpload(ArrayList<TaskItem> items){
        for (TaskItem task: items){
            AVObject o = new AVObject("Tasks");
            o.put("title",task.title);
            o.put("description",task.description);
            o.put("starCount",task.star_count);
            o.put("authorName",task.author_name);
            o.put("authorHead",task.author_head);
            o.put("detail",task.info);

            o.saveInBackground().subscribe(new Observer<AVObject>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(AVObject avObject) {
                    Log.i(TAG,avObject.getObjectId());
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
}
