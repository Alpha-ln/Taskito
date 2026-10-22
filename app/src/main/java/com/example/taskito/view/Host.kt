package com.example.taskito.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskito.R
import com.example.taskito.databinding.FragmentHostBinding
import com.example.taskito.view.adapter.TaskAdapter
import com.example.taskito.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Host : Fragment() {

    private var _binding: FragmentHostBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by activityViewModels()

    private val taskAdapter by lazy {
        TaskAdapter { task ->
            viewModel.toggleTaskCompletion(task)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                if (position == RecyclerView.NO_POSITION) return
                val task = taskAdapter.currentList[position]
                viewModel.deleteTask(task)

                Snackbar.make(binding.root, "Task deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        viewModel.addTask(task)
                    }
                    .show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)

        binding.addNewTaskOfDaySelected.setOnClickListener {
            findNavController().navigate(R.id.addTaskItem)
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val sdf = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
            binding.taskOfDaySelected.text = sdf.format(calendar.time)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tasks.collect {
                taskAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
