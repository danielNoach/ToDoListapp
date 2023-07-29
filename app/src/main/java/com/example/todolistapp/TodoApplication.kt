package com.example.todolistapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.todolistapp.LocalDB.TaskItemDatabase
import com.example.todolistapp.LocalDB.TaskItemRepository

class TodoApplication: Application()
{
    private val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channelID = "task_notification_channel"
            val channelName = "Task Notification"
            val importance =  NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, channelName, importance).apply {
                description = "Reminders for task due dates"
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}