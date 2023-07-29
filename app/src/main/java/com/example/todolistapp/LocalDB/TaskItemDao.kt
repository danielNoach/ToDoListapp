package com.example.todolistapp.LocalDB

import androidx.room.*
import com.example.todolistapp.models.TaskItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskItemDao
{
    @Query("SELECT * FROM task_item_table ORDER BY id ASC")
    fun allTaskItem(): Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskItem(taskItem: TaskItem)

    @Update
    suspend fun updateTaskItem(taskItem: TaskItem)

    @Delete
    suspend fun deleteTaskItem(taskItem: TaskItem)
}