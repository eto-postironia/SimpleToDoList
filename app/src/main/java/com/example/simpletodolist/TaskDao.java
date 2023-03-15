package com.example.simpletodolist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

// Data access object - совокупность методов для работы с базой данных
@Dao
public interface TaskDao {

    // Метод добавления задачи в таблицу
    @Insert
    Completable addTask(Task task);

    // Метод чтения необходимой задачи из таблицы по id
    @Query("SELECT * FROM task_table WHERE id = :id")
    Observable<Task> getTaskById(int id);

    // Метод чтения всех задач из таблицы
    @Query("SELECT * FROM task_table")
    Observable<List<Task>> getAllTasks();

    // Метод изменения значения isCompleted (статус выполнения текущей задачи) в имеющейся записи по id
    @Query("UPDATE task_table SET isCompleted = :isCompleted WHERE id = :id")
    Completable setIsCompleted(boolean isCompleted, int id);

}
