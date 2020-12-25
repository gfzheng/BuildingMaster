package cn.edu.sysu.buildingmaster.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.Arrays;

import cn.edu.sysu.buildingmaster.Activity.LoginActivity;
import cn.edu.sysu.buildingmaster.Bean.User;
import cn.edu.sysu.buildingmaster.Modules.LoginModule;
import cn.edu.sysu.buildingmaster.R;
import cn.edu.sysu.buildingmaster.Util.BitmapHelper;
import cn.edu.sysu.buildingmaster.Util.SharePreferenceUtil;

public class LoginFragment extends Fragment {
    private LoginActivity activity;
    private Bitmap bitmap = null;
    private ImageView head;
    private View.OnClickListener listener;

    public LoginFragment(LoginActivity activity){
        this.activity = activity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        head = view.findViewById(R.id.head);
        final EditText usernameEditText = view.findViewById(R.id.username);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final Button loginButton = view.findViewById(R.id.login);
        final ProgressBar loadingProgressBar = view.findViewById(R.id.loading);

        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    LoginModule.isRegisted(usernameEditText.getText().toString(),head,activity,listener);
                }
            }
        });

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1023);
            }
        };

        head.setOnClickListener(listener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      //          loadingProgressBar.setVisibility(View.VISIBLE);
                if (usernameEditText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"用户名不能为空。",Toast.LENGTH_LONG).show();
                    return;
                }
                if (passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"密码不能为空。",Toast.LENGTH_LONG).show();
                    return;
                }
                if (head.isClickable()){
                    if (bitmap != null){
                        LoginModule.registe(new User(usernameEditText.getText().toString(),passwordEditText.getText().toString(),bitmap),activity);
                    }else {
                        LoginModule.registe(new User(usernameEditText.getText().toString(),passwordEditText.getText().toString(), BitmapFactory.decodeResource(getResources(),R.drawable.head)),activity);
                    }
                }else{
                    LoginModule.login(usernameEditText.getText().toString(),passwordEditText.getText().toString(),activity);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1023){
            Uri uri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri));
                head.setImageBitmap(bitmap);
                this.bitmap = bitmap;
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}