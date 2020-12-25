package cn.edu.sysu.buildingmaster.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class BitmapHelper {
    /*
     *Bitmap转byte数组
     */
    public static byte[] BitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Log.e("test", Arrays.toString(bytes));
        return baos.toByteArray();
    }
    /*
     *byte数组转Bitmap
     */
    public static Bitmap BytesToBitmap(byte[] bis){
        Bitmap map = BitmapFactory.decodeByteArray(bis, 0, bis.length);
        return map;
    }
}
