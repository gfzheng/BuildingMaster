package cn.edu.sysu.buildingmaster.Modules;

import android.app.Application;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;

public class AvosCloudInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AVOSCloud.initialize(this,"Bcmb2UAnkexSOQojWhDOzyqq-9Nh9j0Va","jHxMdH1yraACm21axAEhVadV","https://bcmb2uan.lc-cn-e1-shared.com");
        AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
    }
}
