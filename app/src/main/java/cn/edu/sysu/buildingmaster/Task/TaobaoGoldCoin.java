package cn.edu.sysu.buildingmaster.Task;

import cn.edu.sysu.buildingmaster.Modules.Operation.OperationBuilder;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.GestureOperation;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationClasses.GlobalAction;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationFactory.OperationFactory;

public class TaobaoGoldCoin {
    private static final String TAG = "TaobaoGoldCoin";
    public static OperationBuilder collectGoldCoin(){
        OperationBuilder builder = OperationBuilder.create().setPkgName("com.taobao.taobao")
                .setClassName("com.taobao.tao.TBMainActivity")
                .next(OperationFactory.create(GestureOperation.class).add(360,340).select(GestureOperation.CLICK).setDelay(10000))
                .next(OperationFactory.create(GestureOperation.class).add(360,880).select(GestureOperation.CLICK).setDelay(10000))
                .next(OperationFactory.create(GestureOperation.class).add(610,620).select(GestureOperation.CLICK).setDelay(10000))
                .next(OperationFactory.create(GlobalAction.class).add(GlobalAction.ACTION_BACK).setDelay(5000))
                .next(OperationFactory.create(GestureOperation.class).add(610,620).select(GestureOperation.CLICK).setDelay(10000))
                .next(OperationFactory.create(GlobalAction.class).add(GlobalAction.ACTION_BACK).setDelay(5000))
                .next(OperationFactory.create(GestureOperation.class).add(610,620).select(GestureOperation.CLICK).setDelay(10000))
                .next(OperationFactory.create(GlobalAction.class).add(GlobalAction.ACTION_BACK).setDelay(5000))
                .next(OperationFactory.create(GestureOperation.class).add(540,1800,540,200).select(GestureOperation.SCROLL).setDelay(20000))
                .next(OperationFactory.create(GlobalAction.class).add(GlobalAction.ACTION_BACK));
        return builder;
    }
}
