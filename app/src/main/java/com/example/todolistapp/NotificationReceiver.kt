package com.example.todolistapp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val taskName = intent.getStringExtra("task_name") ?: ""
            showNotification(context, taskName)
        }
    }

    private fun showNotification(context: Context, taskName: String) {
        val channelId = "task_notification_channel"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_check_24)
            .setContentTitle("Task Due")
            .setContentText("Task '$taskName' is due now.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationId = generateRandomNotificationId()
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Show a dialog to inform the user about the need for the permission
            showPermissionDialog(context)
            return
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun generateRandomNotificationId(): Int {
        return System.currentTimeMillis().toInt()
    }

    private fun showPermissionDialog(context: Context) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Permission Required")
        dialogBuilder.setMessage("To show notifications, the app needs the permission to post notifications. Please grant the permission.")
        dialogBuilder.setPositiveButton("OK") { _: DialogInterface, _: Int ->
            // Request the notification permission
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                PERMISSION_REQUEST_CODE
            )
        }
        dialogBuilder.setCancelable(false)
        dialogBuilder.show()
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }
}


