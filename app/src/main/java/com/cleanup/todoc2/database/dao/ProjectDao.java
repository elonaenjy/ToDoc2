package com.cleanup.todoc2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.cleanup.todoc2.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project;")
    LiveData<List<Project>> getAllProjects();

}
