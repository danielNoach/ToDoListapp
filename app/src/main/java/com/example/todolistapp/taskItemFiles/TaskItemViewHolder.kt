package com.example.todolistapp.taskItemFiles

import android.content.Context
import android.graphics.Paint
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.TaskItemCellBinding
import com.example.todolistapp.models.TaskItem
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root)
{
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormat = DateTimeFormatter.ofPattern("MMM d, yyyy")
    fun bindTaskItem(taskItem: TaskItem)
    {
        binding.taskCellName.text = taskItem.name

        if (taskItem.isCompleted()){
            binding.taskCellName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.taskCellDueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.taskCellDueDate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener{
            clickListener.completeTaskItem(taskItem)
        }

        binding.taskCellContainer.setOnClickListener {
            if (taskItem.isCompleted()) {
                Toast.makeText(context, "Task is completed!", Toast.LENGTH_SHORT).show()
            } else {
                clickListener.editTaskItem(taskItem)
            }
        }

        if (taskItem.dueTime() != null)
            binding.taskCellDueTime.text = timeFormat.format(taskItem.dueTime())
        else
            binding.taskCellDueTime.text = ""

        if (taskItem.dueDate() != null)
            binding.taskCellDueDate.text = dateFormat.format(taskItem.dueDate())
        else
            binding.taskCellDueDate.text = ""
    }
}