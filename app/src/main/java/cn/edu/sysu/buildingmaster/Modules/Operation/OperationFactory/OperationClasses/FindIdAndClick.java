package cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;

import com.google.gson.JsonObject;

import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.BaseOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationID;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.Operations;

public class FindIdAndClick extends BaseOperation {
    private static final String TAG = "FindIdAndClick";
    private String id;
    private boolean isLongClick = false;

    public FindIdAndClick(){
        super(OperationID.FIND_VIEWID_AND_CLICK);
    }

    public FindIdAndClick add(String id){
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public FindIdAndClick setLongClick(boolean longClick){
        isLongClick = longClick;
        Log.i(TAG,"Set long click "+longClick);
        return this;
    }

    @Override
    public boolean run(AccessibilityService service) {
        if (id==null || id.isEmpty() ){
            Log.i(TAG,"id is null");
            return false;
        }
        if (isLongClick){
            Log.i("FindIdAndClick","Long click");
            return Operations.findViewIdAndLongClick(service,id);
        }else{
            return Operations.findViewIdAndClick(service,id);
        }

    }

    @Override
    public JsonObject toGsonObj() {
        JsonObject obj = super.toGsonObj();
        obj.addProperty("id",id);
        obj.addProperty("longClick",isLongClick);
        return obj;
    }
}
