package com.cleanup.todoc2.toDoList.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc2.database.dao.TaskDao;
import com.cleanup.todoc2.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDao taskDao;
    private final MutableLiveData<Integer> taskListMutableLiveData = new MutableLiveData<>();

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // --- GET ---
    public LiveData<List<Task>> getTasks() { return this.taskDao.getAllTasksDefault(); }

    public LiveData<List<Task>> getTasksAZ() {
        return this.taskDao.getTasksAlphabeticalAZ();
    }

    public LiveData<List<Task>> getTasksZA() {
        return this.taskDao.getTasksAlphabeticalZA();
    }

    public LiveData<List<Task>> getTasksNewOld() {
        return this.taskDao.getTasksNewToOld();
    }

    public LiveData<List<Task>> getTasksOldNew() {
        return this.taskDao.getTasksOldToNew();
    }

    // --- CREATE ---
    public void addTask(Task task) {
        taskDao.insertTask(task);
    }

    // --- DELETE ---
    public void deleteTask(long id) {
        taskDao.deleteTask(id);
    }

}

