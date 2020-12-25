package cn.edu.sysu.buildingmaster.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sysu.buildingmaster.Adapter.BrowseTaskRecyclerViewAdapter;
import cn.edu.sysu.buildingmaster.Adapter.TaskItemRecyclerViewAdapter;
import cn.edu.sysu.buildingmaster.Adapter.ViewPagerAdapter;
import cn.edu.sysu.buildingmaster.Fragment.BrowseFragment;
import cn.edu.sysu.buildingmaster.Fragment.MineFragment;
import cn.edu.sysu.buildingmaster.Fragment.MyTaskFragment;
import cn.edu.sysu.buildingmaster.Fragment.dummy.OnlineTaskContent;
import cn.edu.sysu.buildingmaster.Fragment.dummy.TaskContent;
import cn.edu.sysu.buildingmaster.R;
import cn.edu.sysu.buildingmaster.Util.SharePreferenceUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!SharePreferenceUtil.isLogined(getApplicationContext())){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

        final ViewPager2 viewpager = findViewById(R.id.viewPager);
        final BottomNavigationView bottombar = findViewById(R.id.bottomNavigationView);

        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.task:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.browse:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.my:
                        viewpager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottombar.setSelectedItemId(R.id.task);
                        break;
                    case 1:
                        bottombar.setSelectedItemId(R.id.browse);
                        break;
                    case 2:
                        bottombar.setSelectedItemId(R.id.my);
                        break;
                }
                super.onPageSelected(position);
            }
        });

        TaskItemRecyclerViewAdapter TaskAdapter = new TaskItemRecyclerViewAdapter(TaskContent.ITEMS);
        BrowseTaskRecyclerViewAdapter browseAdapter = new BrowseTaskRecyclerViewAdapter(OnlineTaskContent.ITEMS,TaskAdapter);
        TaskAdapter.setRelateAdapter(browseAdapter);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MyTaskFragment.newInstance(TaskAdapter));
        fragments.add(BrowseFragment.newInstance(browseAdapter));
        fragments.add(MineFragment.newInstance());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        adapter.setFragments(fragments);
        viewpager.setAdapter(adapter);
    }
}