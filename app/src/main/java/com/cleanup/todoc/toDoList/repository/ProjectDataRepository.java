package com.cleanup.todoc.toDoList.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

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
