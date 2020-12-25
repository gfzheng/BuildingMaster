package cn.edu.sysu.buildingmaster.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class SharePreferenceUtil {

    public static String TAG="SharePreferenceUtil";

    public static String getData(Context context,String key){
        SharedPreferences preferences=context.getSharedPreferences("BuildingMaster",MODE_PRIVATE);
        return preferences.getString(key,"");
    }

    public static void setData(Context context,String key,String value){
        Log.i("test","setData = "+key + "; value = "+value);
        SharedPreferences sharedPreferences = context.getSharedPreferences("BuildingMaster", Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString(key,value);
        editor.apply();
    }

    /**
     * 是否登录过
     * @return
     */
    public static boolean isLogined(Context context){
        SharedPreferences preferences=context.getSharedPreferences("BuildingMaster",MODE_PRIVATE);
        String id=preferences.getString("username","");
        return !id.isEmpty();
    }

}
