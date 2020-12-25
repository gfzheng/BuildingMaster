package cn.edu.sysu.buildingmaster.Modules.Operation;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import cn.edu.sysu.buildingmaster.Activity.MainActivity;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.BaseOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.Operations;
import cn.edu.sysu.buildingmaster.Util.DelayUtil;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 对Operations进行二次封装，通过该类中Manager可以实现链式调用，使得流程代码更简洁.
 * 使用：OperationBuilder.create(service)
 *           .next(方法id和参数)
 *           .build();
 *  build()函数会返回Boolean类型的变量，用于显示执行过程成功或失败
 */

public class OperationBuilder {
    private AccessibilityService service;
    private ArrayList<BaseOperation> operationList;
    private static final String TAG = "OperationBuilder";
    private String pkgName;
    private String className;

    //私有构造函数
    private OperationBuilder(){
        this.operationList = new ArrayList<>();
    }

    /**
     * 设置service引用
     *
     * @param service
     */
    public void setService(AccessibilityService service){
        this.service = service;
    }


    public OperationBuilder setPkgName(String pkgName){
        this.pkgName = pkgName;
        return this;
    }

    public OperationBuilder setClassName(String className){
        this.className =  className;
        return this;
    }

    /**
     * 返回一个新的Builder实例
     */
    public static OperationBuilder create(){
        return new OperationBuilder();
    }

    /**
     * 返回一个新的Builder实例
     *
     * @param service
     */
    public static OperationBuilder create(AccessibilityService service){
        OperationBuilder builder = new OperationBuilder();
        builder.setService(service);
        return builder;
    }

    /**
     * 为Builder对象添加流程对象
     */
    public OperationBuilder next(BaseOperation operation){
        this.operationList.add(operation);
        return this;
    }

    public boolean build(){
        if (service == null){
            Log.e(TAG,"Lack of service");
            return false;
        }
        jumpTo(service,pkgName,className);
        DelayUtil.customerDelay(5000);
        for (BaseOperation operation : operationList){
            if (!operation.run(service)){
                if (!operation.isOptional()){
                    Log.e(TAG,"Perform Action failed. Please retry!");
                    Operations.performHome(service);
                    DelayUtil.longDelay();
                    startMain(service);
                    return false;
                }
            }
            if (operation.getDelay() != 0){
                DelayUtil.customerDelay(operation.getDelay());
            }else{
                DelayUtil.longDelay();
            }
        }
        startMain(service);
        return true;
    }

    public void jumpTo(AccessibilityService service,String pkgName,String className) {
        Intent intent = new Intent();
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(pkgName, className);
        service.startActivity(intent);
    }

    public void startMain(AccessibilityService service){
        Intent intent = new Intent();
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(service.getPackageName(), MainActivity.class.getName());
        service.startActivity(intent);
    }

    public JsonObject toJsonObj(){
        JsonObject obj = new JsonObject();
        obj.addProperty("pkgName",pkgName);
        obj.addProperty("className",className);
        JsonArray array = new JsonArray();
        for (BaseOperation o : operationList){
            array.add(o.toGsonObj());
        }
        obj.add("operationList",array);
        return obj;
    }
}
