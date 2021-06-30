package com.cleanup.todoc2.toDoList.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc2.database.dao.ProjectDao;
import com.cleanup.todoc2.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // --- GET PROJECT LIST---
    public LiveData<List<Project>> getAllProjects() {
        return this.projectDao.getAllProjects();
    }

}
