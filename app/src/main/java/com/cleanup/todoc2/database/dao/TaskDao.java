package com.cleanup.todoc2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc2.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasksDefault();

//    @Query("SELECT * FROM Task inner join Project on Task.projectId = Project.id GROUP BY Project.id")
//    LiveData<List<Task>> getAllTasksByProjectName();

    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    void deleteTask(long taskId);

    @Query("SELECT * FROM Task ORDER BY name ASC")
    LiveData<List<Task>> getTasksAlphabeticalAZ();

    @Query("SELECT * FROM Task ORDER BY name DESC")
    LiveData<List<Task>> getTasksAlphabeticalZA();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTasksNewToOld();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTasksOldToNew();

    @Query("SELECT * FROM Task WHERE id = :taskId")
    LiveData<Task> getTask(long taskId);
}
