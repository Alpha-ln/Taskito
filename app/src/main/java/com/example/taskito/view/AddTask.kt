package com.example.taskito.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.taskito.R
import com.example.taskito.databinding.FragmentAddTaskBinding
import com.example.taskito.model.Task
import com.example.taskito.viewmodel.TaskViewModel
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

class AddTask : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by activityViewModels()

    private var selectedDueDate: Long = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        // Back Button
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        // Date Picker Init
        updateDateDisplay()

        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedDueDate = calendar.timeInMillis
            updateDateDisplay()
        }

        binding.deadlineEditText.setOnClickListener {
            val dialog = DatePickerDialog(
                requireContext(),
                R.style.CustomDatePickerTheme,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE)?.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.black)
            )
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)?.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.black)
            )
        }

        // Save Button
        binding.saveTaskButton.setOnClickListener {
            saveTask()
        }
    }

    private fun updateDateDisplay() {
        binding.deadlineEditText.setText(DateFormat.getDateInstance().format(Date(selectedDueDate)))
    }

    private fun saveTask() {
        val title = binding.titleEditText.text.toString().trim()
        val description = binding.descriptionEditText.text.toString().trim()
        val color = binding.colorSpinner.selectedItem?.toString() ?: "Green"

        if (title.isEmpty()) {
            Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val task = Task(
            title = title,
            description = description,
            dueDate = selectedDueDate,
            taskColor = color,
            createdAt = System.currentTimeMillis()
        )

        viewModel.addTask(task)
        Toast.makeText(requireContext(), "Task saved", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
