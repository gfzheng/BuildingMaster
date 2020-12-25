package cn.edu.sysu.buildingmaster.Modules.OperationFactory.OperationClasses;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;

import com.google.gson.JsonObject;

import cn.edu.sysu.buildingmaster.Modules.OperationFactory.BaseOperation;
import cn.edu.sysu.buildingmaster.Modules.OperationFactory.OperationID;
import cn.edu.sysu.buildingmaster.Modules.Operations;

public class FindIdAndClick extends BaseOperation {
    private static final String TAG = "FindIdAndClick";
    private String id;

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

    @Override
    public boolean run(AccessibilityService service) {
        if (id==null || id.isEmpty() ){
            Log.i(TAG,"id is null");
            return false;
        }
        return Operations.findViewIdAndClick(service,id);
    }

    @Override
    public JsonObject toGsonObj() {
        JsonObject obj = super.toGsonObj();
        obj.addProperty("id",id);
        return obj;
    }
}
