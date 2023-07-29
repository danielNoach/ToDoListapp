package com.example.todolistapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.databinding.FragmentNewTaskSheetBinding
import com.example.todolistapp.models.TaskItem
import com.example.todolistapp.taskItemFiles.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.LocalTime

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null
    private var pickeDate: LocalDate? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null){
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.taskSheetTaskName.text = editable.newEditable(taskItem!!.name)
            binding.taskSheetTaskDesc.text = editable.newEditable(taskItem!!.desc)
            if (taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }

            if (taskItem!!.dueDate() != null){
                pickeDate = taskItem!!.dueDate()!!
                updateDateButtonText()
            }
        }
        else{
            binding.taskTitle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveAction()
        }

        binding.timePickerButton.setOnClickListener{
            openTimePicker()
        }

        binding.datePickerButton.setOnClickListener {
            openDatePicker()
        }
    }

    private fun openDatePicker(){
        if (pickeDate == null)
            pickeDate = LocalDate.now()
        val listener =  DatePickerDialog.OnDateSetListener{_, year, month, dayOfMonth ->
            pickeDate = LocalDate.of(year, month + 1, dayOfMonth)
            updateDateButtonText()
        }

        val dialog = DatePickerDialog(requireActivity(), listener, pickeDate!!.year, pickeDate!!.monthValue - 1, pickeDate!!.dayOfMonth )
        dialog.setTitle("Task Date")
        dialog.show()
    }

    private fun updateDateButtonText() {
        binding.datePickerButton.text = pickeDate.toString()
    }

    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()
        val  listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }

        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction()
    {
        val name = binding.taskSheetTaskName.text.toString()
        val desc = binding.taskSheetTaskDesc.text.toString()
        val dueTimeString = if(dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
        val dueDateString = if(pickeDate == null) null else TaskItem.dateFormatter.format(pickeDate)
        if(taskItem == null)
        {
            val newTask = TaskItem(name,desc,dueTimeString, dueDateString, null,)
            taskViewModel.addTaskItem(newTask, requireContext())
        }
        else{
            taskItem!!.name = name
            taskItem!!.desc= desc
            taskItem!!.dueTimeString = dueTimeString
            taskItem!!.dueDateString = dueDateString
            taskViewModel.updateTaskItem(taskItem!!)
        }

        binding.taskSheetTaskName.setText("")
        binding.taskSheetTaskDesc.setText("")
        dismiss()
    }

}