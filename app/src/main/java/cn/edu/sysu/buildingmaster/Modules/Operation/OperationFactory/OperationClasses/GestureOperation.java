package cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Path;
import android.util.Log;

import com.google.gson.JsonObject;

import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.BaseOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationID;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.Operations;

public class GestureOperation extends BaseOperation {
    private final static String TAG = "GestureOperation";
    private Path path;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int startTime = 500;
    private int duration = 100;
    private int operation_id;

    public GestureOperation(){
        super(OperationID.GESTURE_ACTION);
    }

    public GestureOperation add(int x,int y){
        this.startX = x;
        this.startY = y;
        return this;
    }

    public GestureOperation add(int startX,int startY,int endX,int endY){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        return this;
    }

    public GestureOperation setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public GestureOperation setStartTime(int startTime){
        this.startTime = startTime;
        return this;
    }

    @Override
    public boolean run(AccessibilityService service) {
        Path _path = new Path();
        switch (operation_id){
            case CLICK:
                _path.moveTo(startX,startY);
                _path.lineTo(startX,startY);
                return Operations.gestureAction(service,_path,startTime,duration);
            case LONG_CLICK:
                _path.moveTo(startX,startY);
                _path.lineTo(startX,startY);
                duration = 2000;
                return Operations.gestureAction(service,_path,startTime,duration);
            case SCROLL:
                _path.moveTo(startX,startY);
                _path.lineTo(endX,endY);
                duration = 1000;
                return Operations.gestureAction(service,_path,startTime,duration);
            default:
                Log.e(TAG,"Unrecognize method id");
                return false;
        }
    }

    public GestureOperation select(int operation_id){
        this.operation_id = operation_id;
        return this;
    }

    @Override
    public JsonObject toGsonObj() {
        JsonObject obj = super.toGsonObj();
        obj.addProperty("startX",startX);
        obj.addProperty("startY",startY);
        obj.addProperty("endX",endX);
        obj.addProperty("endY",endY);
        obj.addProperty("startTime",startTime);
        obj.addProperty("duration",duration);
        obj.addProperty("operation_id",operation_id);
        return obj;
    }

    public static final int CLICK = 1;
    public static final int LONG_CLICK = 2;
    public static final int SCROLL = 3;

}
