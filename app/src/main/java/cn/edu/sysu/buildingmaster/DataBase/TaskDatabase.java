package cn.edu.sysu.buildingmaster.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskItem.class},version = 1,exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {
    private static volatile TaskDatabase instance;
    private static final String DATABASE_NAME = "BuildingMaster";

    public abstract TaskDao getDao();

    public static TaskDatabase getInstance(final Context context){
        if (instance == null){
            synchronized (TaskDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context,TaskDatabase.class,DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
