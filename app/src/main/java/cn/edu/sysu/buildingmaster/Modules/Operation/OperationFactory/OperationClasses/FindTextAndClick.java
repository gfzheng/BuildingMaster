package cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;

import com.google.gson.JsonObject;

import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.BaseOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationID;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.Operations;

public class FindTextAndClick extends BaseOperation {
    private static final String TAG = "FindTextAndClick";
    private String text;

    public FindTextAndClick(){
        super(OperationID.FIND_TEXT_AND_CLICK);
    }

    public FindTextAndClick add(String text){
        this.text = text;
        return this;
    }

    @Override
    public boolean run(AccessibilityService service) {
        if (text == null || text.isEmpty()){
            Log.i(TAG,"text is null");
            return false;
        }
        return Operations.findTextAndClick(service,text);
    }

    @Override
    public JsonObject toGsonObj() {
        JsonObject obj = super.toGsonObj();
        obj.addProperty("text",text);
        return obj;
    }
}
