package com.example.simpletodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создание экземпляра базы данных и DAO для работы с ними
        TaskDB taskDB = TaskDB.getInstance(this);
        TaskDao taskDao = taskDB.taskDao();

        // RX-цепочка для записи задачи в базу данных
        // Без observeOn, так как нам не нужен результат выполнения
        taskDao
            .addTask(
                new Task(
                    "New task",
                    "New task description",
                    false
                )
            )
            .subscribeOn(Schedulers.io())
            .subscribe();


        // RX-цепочка для чтения записи из базы данных
        // onNext срабатывает при успешном обновлении observable
        taskDao
            .getTaskById(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onNext);
    }

    private void onNext(Task task) {
        Log.d("MyLog", task.toString());
    }
}