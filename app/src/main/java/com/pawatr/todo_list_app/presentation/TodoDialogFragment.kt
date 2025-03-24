package com.pawatr.todo_list_app.presentation

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.pawatr.todo_list_app.data.model.Note
import com.pawatr.todo_list_app.databinding.DialogTodoFragmentBinding
import java.util.Calendar

class TodoDialogFragment(
    private val todo: Note? = null,
    private val onSave: (Note) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogTodoFragmentBinding
    private var timePickerDialog: TimePickerDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTodoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @SuppressLint("ObsoleteSdkInt", "DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)

        todo?.let {
            binding.titleEditText.setText(it.title)
            binding.descriptionEditText.setText(it.description)

            calendar.set(Calendar.HOUR_OF_DAY, it.timeHour)
            calendar.set(Calendar.MINUTE, it.timeMinute)

            hour = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(binding.root.context, { _, hourOfDay, newMinute ->
                    val newTimeString = String.format("%02d:%02d", hourOfDay, newMinute)
                    binding.timeTextView.text = newTimeString
                    hour = hourOfDay
                    minute = newMinute
                },
                hour,
                minute,
                true // true for 24-hour format, false for 12-hour format
            )
            binding.timeTextView.setOnClickListener {
                timePickerDialog?.show()
            }
        }

        val timeString = String.format("%02d:%02d", hour, minute)
        binding.timeTextView.text = timeString

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            val isCompleted = todo?.isCompleted ?: false
            if (title.isNotEmpty()) {
                val newTodo = todo?.copy(title = title, description = description, timeHour = hour, timeMinute = minute, isCompleted = isCompleted)
                    ?: Note(title = title, description = description, timeHour = hour, timeMinute = minute, isCompleted = isCompleted)
                onSave(newTodo)
                dismiss()
            }
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}