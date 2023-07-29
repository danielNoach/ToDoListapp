package com.example.todolistapp.taskItemFiles

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.example.todolistapp.LocalDB.TaskItemRepository
import com.example.todolistapp.NotificationReceiver
import com.example.todolistapp.models.TaskItem
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class TaskViewModel(private val repository: TaskItemRepository): ViewModel() {
    var taskItems: LiveData<List<TaskItem>> = repository.allTaskItem.asLiveData()

    fun addTaskItem(newTask: TaskItem, context: Context) = viewModelScope.launch {
        repository.insertTaskItem(newTask)
        scheduleNotification(newTask, context)
    }


    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(taskItem)
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if (!taskItem.isCompleted())
            taskItem.completedDateString = TaskItem.dateFormatter.format(LocalDate.now())
        repository.updateTaskItem(taskItem)
    }

    private fun scheduleNotification(taskItem: TaskItem, context: Context) {
        val dueDate = taskItem.dueDate()
        val dueTime = taskItem.dueTime()

        if (dueDate != null && dueTime != null){
            val notificationTimeMillis = calculateNotificationTimeMillis(dueDate, dueTime)
            if (notificationTimeMillis > System.currentTimeMillis()){
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val notificationIntent = Intent(context, NotificationReceiver::class.java)
                notificationIntent.putExtra("task_name", taskItem.name)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    taskItem.id,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    notificationTimeMillis,
                    pendingIntent
                )
            }
        }
    }

    private fun calculateNotificationTimeMillis(dueDate: LocalDate, dueTime: LocalTime): Long {
        val dateMillis = dueDate.atTime(dueTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return dateMillis - System.currentTimeMillis()
    }
}

class TaskItemModelFactory(private val repository: TaskItemRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

        throw  IllegalAccessException("Unknown Class for View Model")
    }

}