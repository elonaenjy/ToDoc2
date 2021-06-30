package com.cleanup.todoc.injections;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.toDoList.repository.ProjectDataRepository;
import com.cleanup.todoc.toDoList.repository.TaskDataRepository;
import com.cleanup.todoc.toDoList.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

        private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
        private final Executor executor;

    public ViewModelFactory(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
            this.projectDataSource = projectDataSource;
            this.taskDataSource = taskDataSource;
            this.executor = executor;
        }


    @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(TaskViewModel.class)) {
                return (T) new TaskViewModel(projectDataSource, taskDataSource, executor);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }





