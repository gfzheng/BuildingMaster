package cn.edu.sysu.buildingmaster.Bean;

import android.graphics.Bitmap;

public class User {
    public String username;
    public String password;
    public Bitmap head;

    public User(String username,String password,Bitmap head){
        this.username = username;
        this.password = password;
        this.head =  head;
    }
}
