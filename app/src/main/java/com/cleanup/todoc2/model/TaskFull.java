package com.cleanup.todoc2.model;

import java.util.Objects;

public class TaskFull {
    private long idTask;
    private long idProject;
    private long creationTimestamp;
    private String nameTask;
    private long color;
    private String nameProject;

    public long getIdTask() {
        return idTask;
    }

    public void setIdTask(long idTask) {
        this.idTask = idTask;
    }

    public long getIdProject() {
        return idProject;
    }

    public void setIdProject(long idProject) {
        this.idProject = idProject;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public long getColor() {
        return color;
    }

    public void setColor(long color) {
        this.color = color;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public TaskFull(long idTask, long idProject, long creationTimestamp, String nameTask, long color, String nameProject) {
        this.idTask = idTask;
        this.idProject = idProject;
        this.creationTimestamp = creationTimestamp;
        this.nameTask = nameTask;
        this.color = color;
        this.nameProject = nameProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskFull taskFull = (TaskFull) o;
        return idTask == taskFull.idTask &&
                idProject == taskFull.idProject &&
                creationTimestamp == taskFull.creationTimestamp &&
                color == taskFull.color &&
                nameTask.equals(taskFull.nameTask) &&
                nameProject.equals(taskFull.nameProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, idProject, creationTimestamp, nameTask, color, nameProject);
    }
}

