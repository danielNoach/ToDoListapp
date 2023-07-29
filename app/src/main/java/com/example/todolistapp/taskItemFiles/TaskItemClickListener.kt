package com.example.todolistapp.taskItemFiles

import com.example.todolistapp.models.TaskItem

interface TaskItemClickListener
{
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
}