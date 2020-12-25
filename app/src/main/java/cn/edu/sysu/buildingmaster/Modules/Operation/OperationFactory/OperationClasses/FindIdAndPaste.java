package cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;

import com.google.gson.JsonObject;

import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.BaseOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationID;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.Operations;

public class FindIdAndPaste extends BaseOperation {
    private static final String TAG = "FindIdAndPaste";
    private String id;
    private String text;
    public FindIdAndPaste(){
        super(OperationID.FIND_VIEWID_AND_PASTE);
    }


    public FindIdAndPaste add(String id,String text){
        this.id = id;
        this.text = text;
        return this;
    }


    @Override
    public boolean run(AccessibilityService service) {
        if (id==null || id.isEmpty() ){
            Log.i(TAG,"id is null");
            return false;
        }
        if (text == null || text.isEmpty()){
            Log.i(TAG,"text is null");
            return false;
        }
        return Operations.findViewByIdAndPasteContent(service,id,text);
    }

    @Override
    public JsonObject toGsonObj() {
        JsonObject obj = super.toGsonObj();
        obj.addProperty("id",id);
        obj.addProperty("text",text);
        return obj;
    }
}
