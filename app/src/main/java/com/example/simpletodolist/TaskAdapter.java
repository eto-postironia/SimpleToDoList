package com.example.simpletodolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpletodolist.databinding.TaskItemBinding;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;


// Стандартный адаптер для RecyclerView
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final ArrayList<Task> data;

    public TaskAdapter(ArrayList<Task> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskItemBinding binding = TaskItemBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent,
            false
        );
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task item = data.get(position);

        holder.binding.tvHeader.setText(item.getTaskName());
        holder.binding.tvDescription.setText(item.getTaskDesc());
        holder.binding.checkbox.setChecked(item.getCompleted());

        // Установка onClickListener на вью внутри элемента списка RecyclerView
        holder.binding.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDB taskDB = TaskDB.getInstance(holder.binding.getRoot().getContext());
                TaskDao taskDao = taskDB.taskDao();

                taskDao
                    .setIsCompleted(holder.binding.checkbox.isChecked(), item.id)
                    .subscribeOn(Schedulers.io())
                    .subscribe();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        TaskItemBinding binding;

        public TaskViewHolder(@NonNull TaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
