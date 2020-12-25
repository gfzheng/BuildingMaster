package cn.edu.sysu.buildingmaster.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface TaskDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    Completable insertTask(TaskItem item);

    @Update
    Completable updateTask(TaskItem item);

    @Query("SELECT * FROM tasks")
    Single<List<TaskItem>> getTasks();

    @Delete
    Completable deleteTask(TaskItem item);
}
