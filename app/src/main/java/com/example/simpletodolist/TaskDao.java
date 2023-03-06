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

}
