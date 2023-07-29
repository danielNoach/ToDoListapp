package com.example.todolistapp.LocalDB

import androidx.annotation.WorkerThread
import com.example.todolistapp.LocalDB.TaskItemDao
import com.example.todolistapp.models.TaskItem
import kotlinx.coroutines.flow.Flow


class TaskItemRepository(private val taskItemDao: TaskItemDao)
{
    val allTaskItem: Flow<List<TaskItem>> = taskItemDao.allTaskItem()

    @WorkerThread
    suspend fun insertTaskItem(taskItem : TaskItem){
        taskItemDao.insertTaskItem(taskItem)
    }

    @WorkerThread
    suspend fun updateTaskItem(taskItem : TaskItem){
        taskItemDao.updateTaskItem(taskItem)
    }

//    @WorkerThread
//    suspend fun deleteTaskItem(taskItem : TaskItem){
//        taskItemDao.deleteTaskItem(taskItem)
//    }
}