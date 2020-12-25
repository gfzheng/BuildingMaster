package cn.edu.sysu.buildingmaster.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.edu.sysu.buildingmaster.Fragment.dummy.OnlineTaskContent;
import cn.edu.sysu.buildingmaster.Fragment.dummy.TaskContent;
import cn.edu.sysu.buildingmaster.Modules.UploadData;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TaskContent.initData(getApplicationContext());
        OnlineTaskContent.initData();
 //       UploadData.initData(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();

    }
}
