package cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;

import com.google.gson.JsonObject;

import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.BaseOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationID;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.Operations;

public class FindTextById extends BaseOperation {
    private static final String TAG = "FindTextById";
    private String id;
    private String text;

    public FindTextById(){
        super(OperationID.FIND_TEXT_BY_ID);
    }

    @Override
    public boolean run(AccessibilityService service) {
        if (id == null || id.isEmpty()){
            Log.e(TAG,"Id is null.");
            return false;
        }
        if (text == null || text.isEmpty()){
            Log.e(TAG,"Text is null.");
            return false;
        }
        return text.equals(Operations.findTextById(service,id));
    }

    public FindTextById add(String id,String text){
        this.text = text;
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    @Override
    public JsonObject toGsonObj() {
        JsonObject obj = super.toGsonObj();
        obj.addProperty("id",id);
        obj.addProperty("text",text);
        return obj;
    }
}
