package cn.edu.sysu.buildingmaster.DataBase;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.JsonObject;

import cn.edu.sysu.buildingmaster.Util.Base64Helper;
import cn.edu.sysu.buildingmaster.Modules.Operation.OperationBuilder;

@Entity(tableName = "tasks")
public class TaskItem {
    @NonNull
    @PrimaryKey
    public String id;

    public String title;
    public String description;
    public int star_count;
    public boolean isStar;
    public String author_name;
    public String author_head;
    public String info;

    public TaskItem(){}

    @Ignore
    public TaskItem(@NonNull String id, String title, String description, int star_count, String author_name, boolean isStar, Bitmap author_head, OperationBuilder builder) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.star_count = star_count;
        this.isStar = isStar;
        this.author_name = author_name;
        this.author_head = Base64Helper.BitMapToBase64(author_head);
        this.info = builder.toJsonObj().toString();
    }

    @Ignore
    public TaskItem(@NonNull String id, String title, String description, int star_count, String author_name, boolean isStar, String author_head, String builder) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.star_count = star_count;
        this.isStar = isStar;
        this.author_name = author_name;
        this.author_head = author_head;
        this.info = builder;
    }

    @Ignore
    @Override
    public String toString() {
        JsonObject obj = new JsonObject();
        obj.addProperty("id",id);
        obj.addProperty("title",title);
        obj.addProperty("star_count",star_count);
        obj.addProperty("isStar",isStar);
        obj.addProperty("author_name",author_name);
        obj.addProperty("author_head",author_head);
        obj.addProperty("info",info);
        return obj.toString();
    }
}