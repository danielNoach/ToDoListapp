package com.example.todolistapp.taskItemFiles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.TaskItemCellBinding
import com.example.todolistapp.models.TaskItem

class TaskItemAdapter(
    private val taskItem: List<TaskItem>,
    private val clickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val from =  LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from, parent, false)
        return TaskItemViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int = taskItem.size


    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItem[position])
    }
}