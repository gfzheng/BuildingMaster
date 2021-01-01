package cn.edu.sysu.buildingmaster.Service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.sysu.buildingmaster.Activity.MainActivity;
import cn.edu.sysu.buildingmaster.Bean.EventState;
import cn.edu.sysu.buildingmaster.Task.Wechat;
import cn.edu.sysu.buildingmaster.Util.JsonUtil;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationBuilder;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BuildingService extends AccessibilityService {
    private final static String TAG = "BuildingService";
    private int mDebugDepth;

    private void printAllViews(AccessibilityNodeInfo mNodeInfo) {
        if (mNodeInfo == null) return;
        String log ="";
        for (int i = 0; i < mDebugDepth; i++) {
            log += ".";
        }
        log+="("+mNodeInfo.getText() +" <-- "+
                mNodeInfo.getViewIdResourceName()+")";
        Log.d(TAG, log);
        if (mNodeInfo.getChildCount() < 1) return;
        mDebugDepth++;

        for (int i = 0; i < mNodeInfo.getChildCount(); i++) {
            printAllViews(mNodeInfo.getChild(i));
        }
        mDebugDepth--;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        mDebugDepth = 0;
        AccessibilityNodeInfo mNodeInfo = event.getSource();
        printAllViews(mNodeInfo);
//        Log.v(TAG, mNodeInfo.toString());
        switch (event.getEventType()){
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.i(TAG,"Get TYPE_VIEW_CLICKED:"+event.getSource() + "/" + event.getClassName());
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.i(TAG,"Get TYPE_WINDOW_STATE_CHANGED:"+event.getPackageName() + "/" + event.getClassName());
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.i(TAG,"Get NOtification."+event.getPackageName() + "/" + event.getClassName());
                if (event.getPackageName().toString().equals("com.tencent.mm")){
                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification){
                        Notification notification = (Notification)event.getParcelableData();
                        OperationBuilder builder = Wechat.parseNotification(notification);
                        builder.setService(this);
                        builder.build();
                    }
                }
                break;
            default:
                Log.i(TAG,"EventType = " + event.getEventType());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(EventState state){
        Log.i(TAG,state.id+state.title);
        OperationBuilder builder = JsonUtil.JsonToBuilder(state.detail);
        builder.setService(this);
        builder.build();
    }

    public void startMain(){
        Intent intent = new Intent();
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(getPackageName(), MainActivity.class.getName());
        startActivity(intent);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        EventBus.getDefault().register(this);
        Log.i(TAG,"Service connected.");
        startMain();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        EventBus.getDefault().unregister(this);
        Log.i(TAG,"Service onUnbind.");
        return super.onUnbind(intent);
    }

}
