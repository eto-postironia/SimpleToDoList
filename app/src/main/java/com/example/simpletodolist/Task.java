package com.example.simpletodolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Помеченная для Room сущность базы данных с указанием названия таблицы под неё
@Entity(tableName = "task_table")
public class Task {
    // Автогенерируемый ключ таблицы
    @PrimaryKey(autoGenerate = true)
    int id;
    private String taskName;
    private String taskDesc;
    private Boolean isCompleted;

    public Task(String taskName, String taskDesc, Boolean isCompleted) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.isCompleted = isCompleted;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", taskName='" + taskName + '\'' +
            ", taskDesc='" + taskDesc + '\'' +
            ", isCompleted=" + isCompleted +
            '}';
    }
}
