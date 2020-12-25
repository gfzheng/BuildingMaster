package cn.edu.sysu.buildingmaster.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.edu.sysu.buildingmaster.DataBase.TaskItem;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationBuilder;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.FindIdAndClick;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.FindIdAndPaste;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.FindIdAndTextAndClick;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.FindTextAndClick;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.FindTextById;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.GestureOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.GlobalAction;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationFactory;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationID;

public class JsonUtil {

    public static TaskItem JsonToTask(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,TaskItem.class);
    }

    public static OperationBuilder JsonToBuilder(String json){
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(json,JsonObject.class);
        OperationBuilder builder = OperationBuilder.create();
        builder.setPkgName(obj.get("pkgName").getAsString());
        builder.setClassName(obj.get("className").getAsString());
        JsonArray array = obj.get("operationList").getAsJsonArray();
        for (JsonElement e : array){
            JsonObject o = e.getAsJsonObject();
            switch (o.get("method_id").getAsInt()){
                case OperationID.FIND_TEXT_AND_CLICK:
                    builder.next(OperationFactory.create(FindTextAndClick.class).add(o.get("text").getAsString()).setDelay(o.get("delay").getAsInt()).setOptional(o.get("optional").getAsBoolean()));
                    break;
                case OperationID.FIND_TEXT_BY_ID:
                    builder.next(OperationFactory.create(FindTextById.class).add(o.get("id").getAsString(),o.get("text").getAsString()).setDelay(o.get("delay").getAsInt()).setOptional(o.get("optional").getAsBoolean()));
                    break;
                case OperationID.FIND_VIEWID_AND_CLICK:
                    builder.next(OperationFactory.create(FindIdAndClick.class).add(o.get("id").getAsString()).setLongClick(o.get("longClick").getAsBoolean()).setDelay(o.get("delay").getAsInt()).setOptional(o.get("optional").getAsBoolean()));
                    break;
                case OperationID.FIND_VIEWID_AND_PASTE:
                    builder.next(OperationFactory.create(FindIdAndPaste.class).add(o.get("id").getAsString(),o.get("text").getAsString()).setDelay(o.get("delay").getAsInt()).setOptional(o.get("optional").getAsBoolean()));
                    break;
                case OperationID.FIND_VIEWID_AND_TEXT_AND_CLICK:
                    builder.next(OperationFactory.create(FindIdAndTextAndClick.class).add(o.get("id").getAsString(),o.get("text").getAsString()).setDelay(o.get("delay").getAsInt()).setOptional(o.get("optional").getAsBoolean()));
                    break;
                case OperationID.GESTURE_ACTION:
                    builder.next(OperationFactory.create(GestureOperation.class).add(o.get("startX").getAsInt(),o.get("startY").getAsInt(),o.get("endX").getAsInt(),o.get("endY").getAsInt()).select(o.get("operation_id").getAsInt()).setDuration(o.get("duration").getAsInt()).setStartTime(o.get("startTime").getAsInt()).setDelay(o.get("delay").getAsInt()).setOptional(o.get("optional").getAsBoolean()));
                    break;
                case OperationID.GLOBAL_ACTION:
                    builder.next(OperationFactory.create(GlobalAction.class).add(o.get("action_id").getAsInt()).setDelay(o.get("delay").getAsInt()).setOptional(o.get("optional").getAsBoolean()));
                    break;
            }
        }
        return builder;
    }
}
