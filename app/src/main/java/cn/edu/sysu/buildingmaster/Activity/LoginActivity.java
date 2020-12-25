package cn.edu.sysu.buildingmaster.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.edu.sysu.buildingmaster.Fragment.LoginFragment;
import cn.edu.sysu.buildingmaster.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment,new LoginFragment(this)).commit();
    }
}
