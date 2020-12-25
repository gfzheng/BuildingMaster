package cn.edu.sysu.buildingmaster.Task;

import cn.edu.sysu.buildingmaster.Modules.Operation.OperationBuilder;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.GestureOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.GlobalAction;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationFactory;

public class TmallFarm {
    private static final String TAG = "TmallFarm";
    public static OperationBuilder collectSunshine(){
        OperationBuilder builder = OperationBuilder.create().setPkgName("com.tmall.wireless")
                .setClassName("com.tmall.wireless.splash.TMSplashActivity")
                .next(OperationFactory.create(GestureOperation.class).add(540,1200).select(GestureOperation.CLICK).setDelay(5000))
                .next(OperationFactory.create(GestureOperation.class).add(1000,1000).select(GestureOperation.CLICK).setDelay(5000))
                .next(OperationFactory.create(GestureOperation.class).add(960,1700).select(GestureOperation.CLICK).setDelay(2000))
                .next(OperationFactory.create(GestureOperation.class).add(900,1020).select(GestureOperation.CLICK).setDelay(3000))
                .next(OperationFactory.create(GestureOperation.class).add(540,1800,540,200).select(GestureOperation.SCROLL).setDelay(20000))
                .next(OperationFactory.create(GestureOperation.class).add(90,150).select(GestureOperation.CLICK))
                .next(OperationFactory.create(GestureOperation.class).add(1020,740).select(GestureOperation.CLICK))
                .next(OperationFactory.create(GestureOperation.class).add(50,144).select(GestureOperation.CLICK))
                .next(OperationFactory.create(GlobalAction.class).add(GlobalAction.ACTION_BACK));
        return builder;
    }
}
