package cn.edu.sysu.buildingmaster.Modules;

import android.util.Log;

import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NetModule {

    public static void changeStarCount(String id,int step){
        AVObject o = AVObject.createWithoutData("Tasks",id);
        if (step == 1){
            o.increment("starCount",1);
        } else{
            o.decrement("starCount",1);
        }
        o.saveInBackground().subscribe(new Observer<AVObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AVObject avObject) {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("changeStarCount",e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
