package com.example.simpletodolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.simpletodolist.databinding.FragmentSecondBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SecondFragment extends Fragment {

    Disposable newTaskDisposable;

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        FragmentSecondBinding binding = FragmentSecondBinding.inflate(
            inflater,
            container,
            false
        );

        TaskDB taskDB = TaskDB.getInstance(requireContext());
        TaskDao taskDao = taskDB.taskDao();

        binding.saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskDisposable = taskDao
                    .addTask(
                        new Task(
                            binding.editTextHeader.getText().toString(),
                            binding.editTextDesc.getText().toString(),
                            false
                        )
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onTaskAdded);
            }

            private void onTaskAdded() {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        return binding.getRoot();
    }

    // Не забываем избавиться от всех disposable в конце жизни фрагмента!
    @Override
    public void onDestroy() {
        super.onDestroy();
        newTaskDisposable.dispose();
    }
}
