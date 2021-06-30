package com.cleanup.todoc.toDoList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.toDoList.repository.ProjectDataRepository;
import com.cleanup.todoc.toDoList.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    // Parameter for tasks list order
    public enum SortTaskList {
        DEFAULT,
        TASKS_AZ,
        TASKS_ZA,
        TASKS_NewOld,
        TASKS_OldNew
    }

    public SortTaskList sortTaskList = SortTaskList.DEFAULT;

    // -------------
    // FOR PROJECT
    // -------------
    public LiveData<List<Project>> getProjects() {
        return projectDataSource.getAllProjects();
    }

    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public LiveData<List<Task>> getTaskSorted() {
        switch (sortTaskList) {
            case TASKS_AZ:
                return taskDataSource.getTasksAZ();
            case TASKS_ZA:
                return taskDataSource.getTasksZA();
            case TASKS_NewOld:
                return taskDataSource.getTasksNewOld();
            case TASKS_OldNew:
                return taskDataSource.getTasksOldNew();
            default:
                return taskDataSource.getTasks();
        }
    }

    // -------------
    // FOR TASKS
    // -------------
    public void addTask(Task task) {
        executor.execute(() -> taskDataSource.addTask(task));
    }

    public void deleteTask(long id) {
        executor.execute(() -> taskDataSource.deleteTask(id));
    }

}
