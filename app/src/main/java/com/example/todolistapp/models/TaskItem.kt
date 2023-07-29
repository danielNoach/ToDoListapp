package com.example.todolistapp.models

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolistapp.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "task_item_table")
class TaskItem(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "desc") var desc: String,
    @ColumnInfo(name = "dueTimeString") var dueTimeString: String?,
    @ColumnInfo(name = "dueDateString") var dueDateString: String?,
    @ColumnInfo(name = "completedDateString") var completedDateString: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {


    fun completedDate(): LocalDate? = if (completedDateString == null) null
        else LocalDate.parse(completedDateString, dateFormatter)

    fun dueTime(): LocalTime? = if (dueTimeString == null) null
    else LocalTime.parse(dueTimeString, timeFormatter)

    fun dueDate(): LocalDate? = if (dueDateString == null) null
    else LocalDate.parse(dueDateString, dateFormatter)

    fun isCompleted() = completedDate() != null
    fun imageResource(): Int = if (isCompleted()) R.drawable.baseline_check_24 else R.drawable.baseline_unchecked_24
    fun imageColor(context: Context): Int = if (isCompleted()) teal(context) else black(context)

    private fun teal(context: Context) = ContextCompat.getColor(context, R.color.teal_200)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)

    companion object{
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }
}