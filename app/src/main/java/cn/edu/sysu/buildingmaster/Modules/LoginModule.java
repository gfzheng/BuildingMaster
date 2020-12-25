package cn.edu.sysu.buildingmaster.Modules;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Arrays;
import java.util.List;

import cn.edu.sysu.buildingmaster.Activity.LoginActivity;
import cn.edu.sysu.buildingmaster.Activity.MainActivity;
import cn.edu.sysu.buildingmaster.Bean.User;
import cn.edu.sysu.buildingmaster.Util.Base64Helper;
import cn.edu.sysu.buildingmaster.Util.BitmapHelper;
import cn.edu.sysu.buildingmaster.Util.SharePreferenceUtil;
import cn.leancloud.AVFile;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.callback.ProgressCallback;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginModule {
    private static final String TAG = "LoginModule";
    public static void isRegisted(String username, final ImageView head, final LoginActivity activity, final View.OnClickListener listener){
        AVQuery<AVObject> o = new AVQuery<>("UserInfo");
        o.whereEqualTo("username",username);
        o.getFirstInBackground().subscribe(new Observer<AVObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AVObject avObject) {
                Log.i(TAG,avObject.toJSONString());
                getHead(avObject.getAVFile("head"),head,activity);
                head.setClickable(true);
                head.setOnClickListener(listener);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,e.getMessage());
                head.setClickable(false);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static void registe(final User user, final LoginActivity activity){
        Log.i(TAG,"Begin registe");
        final AVObject o = new AVObject("UserInfo");
        o.put("username",user.username);
        o.put("password",user.password);
        AVFile file = new AVFile("head.jpg", BitmapHelper.BitmapToBytes(user.head));
        o.put("head",file);
        file.saveInBackground(new ProgressCallback() {
            @Override
            public void done(Integer percentDone) {
                if (percentDone == 100){
                    o.saveInBackground().subscribe(new Observer<AVObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(AVObject avObject) {
                            SharePreferenceUtil.setData(activity.getApplicationContext(),"username",user.username);
                            SharePreferenceUtil.setData(activity.getApplicationContext(),"head",Base64Helper.BitMapToBase64(user.head));
                            activity.startActivity(new Intent(activity.getBaseContext(), MainActivity.class));
                            activity.finish();
                            Log.i(TAG,"registe success");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG,e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            }
        });
    }

    public static void login(final String username, final String password, final LoginActivity activity){
        final AVQuery<AVObject> o = new AVQuery<>("UserInfo");
        o.whereEqualTo("username",username);
        o.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<AVObject> avObjects) {
                if (avObjects!= null && avObjects.size()!= 0){
                    if (avObjects.get(0).getString("password").equals(password)){
                        SharePreferenceUtil.setData(activity.getApplicationContext(),"username",username);
                        Toast.makeText(activity.getBaseContext(),"登录成功",Toast.LENGTH_LONG).show();
                        activity.startActivity(new Intent(activity.getBaseContext(), MainActivity.class));
                        activity.finish();
                    }
                }else{
                    Toast.makeText(activity.getBaseContext(),"密码错误，请重试",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(activity.getBaseContext(),"密码错误，请重试",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static void getHead(AVFile file, final ImageView view, final LoginActivity activity){
        Log.i(TAG,file.getUrl());
        Glide.with(activity).asBitmap().load(file.getUrl()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                String s = Base64Helper.BitMapToBase64(resource);
                Bitmap map = Base64Helper.Base64ToBitMap(s);
                view.setImageBitmap(map);
            }
        });
    }
}
