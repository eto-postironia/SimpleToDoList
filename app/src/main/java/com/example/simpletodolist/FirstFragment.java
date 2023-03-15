package com.example.simpletodolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.simpletodolist.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FirstFragment extends Fragment {

    FragmentFirstBinding binding;
    Disposable taskListDisposable;

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(
            inflater,
            container,
            false
        );

        TaskDB taskDB = TaskDB.getInstance(requireContext());
        TaskDao taskDao = taskDB.taskDao();

        // Загружаем все задачи из базы данных
        taskListDisposable = taskDao
            .getAllTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onTasksLoaded);

        binding.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_firstFragment_to_secondFragment);
            }
        });

        return binding.getRoot();
    }

    // Выводим список задач через адаптер в ресайклере
    private void onTasksLoaded(List<Task> tasks) {
        TaskAdapter adapter = new TaskAdapter((ArrayList<Task>) tasks);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerview.setAdapter(adapter);
    }

    // Не забываем избавиться от всех disposable в конце жизни фрагмента!
    @Override
    public void onDestroy() {
        super.onDestroy();
        taskListDisposable.dispose();
    }
}
