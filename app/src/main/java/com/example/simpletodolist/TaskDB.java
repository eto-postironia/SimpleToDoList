package com.example.simpletodolist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Класс базы данных (синглтон)
@Database(entities = {Task.class}, version = 1)
public abstract class TaskDB extends RoomDatabase {

    // Хранимый синглтоном единственный экземпляр
    private static TaskDB instance;

    // Ссылка на DAO
    public abstract TaskDao taskDao();

    // Метод создания и получения экземляра синглтона
    public static synchronized TaskDB getInstance(Context context) {
        if (instance == null) {
            // Инициализация базы данных
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TaskDB.class, "task_database")
                .build();
        }
        return instance;
    }

}
