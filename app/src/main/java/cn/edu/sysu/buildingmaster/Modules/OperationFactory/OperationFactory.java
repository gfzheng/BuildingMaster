package cn.edu.sysu.buildingmaster.Modules.OperationFactory;

import android.util.Log;

import cn.edu.sysu.buildingmaster.Modules.OperationFactory.OperationClasses.FindIdAndClick;
import cn.edu.sysu.buildingmaster.Modules.OperationFactory.OperationClasses.FindIdAndPaste;
import cn.edu.sysu.buildingmaster.Modules.OperationFactory.OperationClasses.FindIdAndTextAndClick;
import cn.edu.sysu.buildingmaster.Modules.OperationFactory.OperationClasses.FindTextAndClick;
import cn.edu.sysu.buildingmaster.Modules.OperationFactory.OperationClasses.FindTextById;
import cn.edu.sysu.buildingmaster.Modules.OperationFactory.OperationClasses.GestureOperation;
import cn.edu.sysu.buildingmaster.Modules.OperationFactory.OperationClasses.GlobalAction;

public class OperationFactory {
    private static final String TAG = "OperationFactory";
    public static <T extends BaseOperation> T create(Class<T> cls){
        BaseOperation operation = null;
        try {
            operation = (BaseOperation) Class.forName(cls.getName()).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        /* switch (operation_id){
            case OperationID.FIND_TEXT_BY_ID:
                operation = new FindTextById(operation_id);
                break;
            case OperationID.FIND_TEXT_AND_CLICK:
                operation = new FindTextAndClick(operation_id);
                break;
            case OperationID.FIND_VIEWID_AND_CLICK:
                operation = new FindIdAndClick(operation_id);
                break;
            case OperationID.FIND_VIEWID_AND_PASTE:
                operation = new FindIdAndPaste(operation_id);
                break;
            case OperationID.FIND_VIEWID_AND_TEXT_AND_CLICK:
                operation = new FindIdAndTextAndClick(operation_id);
                break;
            case OperationID.GESTURE_ACTION:
                operation = new GestureOperation(operation_id);
                break;
            case OperationID.GLOBAL_ACTION:
                operation = new GlobalAction(operation_id);
                break;
            default:
                operation = null;
                break;
        }*/

        return (T)operation;
    }
}
