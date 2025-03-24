package com.pawatr.todo_list_app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawatr.todo_list_app.data.model.Note
import com.pawatr.todo_list_app.databinding.ActivityMainBinding
import com.pawatr.todo_list_app.presentation.adapter.ToDoAdapter
import com.pawatr.todo_list_app.presentation.viewmodel.NoteViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private var todoAdapter = ToDoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        observeTodos()
        setupListeners()
    }

    private fun setupView() = with(binding) {
        todoRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
    }

    private fun observeTodos() {
        lifecycleScope.launch {
            noteViewModel.todos.collect { todos ->
                todoAdapter.todos = todos
            }
        }
    }

    private fun setupListeners() = with(binding) {
        addTodoButton.setOnClickListener {
            val title = newTodoTitleEditText.text.toString()
            if (title.isNotEmpty()) {
                noteViewModel.insertTodo(Note(title = title, description = "", timeHour = 0, timeMinute = 0))
                newTodoTitleEditText.text.clear()
            }
        }

        updateFirstTodoButton.setOnClickListener {
            noteViewModel.todos.value.firstOrNull()?.let {
                noteViewModel.updateTodo(it.copy(isCompleted = true))
            }
        }

        deleteLastTodoButton.setOnClickListener {
            noteViewModel.todos.value.lastOrNull()?.let {
                noteViewModel.deleteTodo(it)
            }
        }
    }
}