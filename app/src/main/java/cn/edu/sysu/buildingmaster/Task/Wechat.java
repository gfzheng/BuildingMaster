package cn.edu.sysu.buildingmaster.Task;

import android.app.Notification;
import android.app.PendingIntent;
import android.util.Log;

import cn.edu.sysu.buildingmaster.Modules.Operation.OperationBuilder;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.FindIdAndClick;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.FindIdAndPaste;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.FindIdAndTextAndClick;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.FindTextAndClick;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.GestureOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.GlobalAction;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationFactory;

public class Wechat {
    private static final String TAG = "Wechat";
    public static OperationBuilder shareMoment(){
        OperationBuilder builder = OperationBuilder.create().setPkgName("com.tencent.mm")
                .setClassName("com.tencent.mm.ui.LauncherUI")
                .next(OperationFactory.create(FindTextAndClick.class).add("发现").setDelay(2000))
                .next(OperationFactory.create(FindTextAndClick.class).add("朋友圈").setDelay(2000))
                .next(OperationFactory.create(FindIdAndClick.class).add("com.tencent.mm:id/cj").setLongClick(true).setDelay(2000))
                .next(OperationFactory.create(FindIdAndPaste.class).add("com.tencent.mm:id/fms","测试发朋友圈").setDelay(2000))
                .next(OperationFactory.create(FindIdAndClick.class).add("com.tencent.mm:id/ch").setDelay(2000))
                .next(OperationFactory.create(FindIdAndClick.class).add("com.tencent.mm:id/dn"));
        return builder;
    }

    public static OperationBuilder autoReply(String name,String content){
        OperationBuilder builder = OperationBuilder.create().setPkgName("com.tencent.mm")
                .setClassName("com.tencent.mm.ui.LauncherUI")
                .next(OperationFactory.create(FindTextAndClick.class).add("搜索").setDelay(2000))
                .next(OperationFactory.create(FindIdAndPaste.class).add("com.tencent.mm:id/bhn",name).setDelay(2000))
                .next(OperationFactory.create(FindIdAndTextAndClick.class).add("com.tencent.mm:id/gbv",name).setDelay(2000))
                .next(OperationFactory.create(FindIdAndPaste.class).add("com.tencent.mm:id/al_","收到内容"+content).setDelay(2000))
                .next(OperationFactory.create(FindTextAndClick.class).add("发送").setDelay(2000))
                .next(OperationFactory.create(FindIdAndClick.class).add("com.tencent.mm:id/rs").setDelay(2000))
                .next(OperationFactory.create(GlobalAction.class).add(GlobalAction.ACTION_BACK));
        return builder;
    }

    public static OperationBuilder parseNotification(Notification notification){
        String content = notification.tickerText.toString();
        Log.i(TAG,"Notification content is: "+content);
        if (content.contains("请求添加你为朋友")){
            PendingIntent intent = notification.contentIntent;
            try {
                intent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
        String[] contentText = content.split(":");
        String name = contentText[0].trim();
        String text = contentText[1].trim();
        if (!name.equals(""))
            Log.i(TAG,"name is: "+name+"; And content is: "+text);
        return autoReply(name,text);
    }
}
