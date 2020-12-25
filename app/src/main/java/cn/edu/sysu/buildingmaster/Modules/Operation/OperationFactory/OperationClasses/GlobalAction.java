package cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;

import com.google.gson.JsonObject;

import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.BaseOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationID;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.Operations;

public class GlobalAction extends BaseOperation {
    private static final String TAG = "GlobalAction";
    private int action_id;

    public GlobalAction(){
        super(OperationID.GLOBAL_ACTION);
    }

    public GlobalAction add(int action_id){
        this.action_id = action_id;
        return this;
    }

    @Override
    public boolean run(AccessibilityService service) {
        switch (action_id){
            case ACTION_BACK:
                return Operations.performBack(service);
            case ACTION_HOME:
                return Operations.performHome(service);
            default:
                Log.e(TAG,"Unregonize action id");
                return false;
        }
    }

    @Override
    public JsonObject toGsonObj() {
        JsonObject obj = super.toGsonObj();
        obj.addProperty("action_id",action_id);
        return obj;
    }

    public static final int ACTION_BACK = 1;
    public static final int ACTION_HOME = 2;
}
