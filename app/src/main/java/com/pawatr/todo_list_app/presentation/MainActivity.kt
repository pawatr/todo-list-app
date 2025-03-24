package com.pawatr.todo_list_app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawatr.todo_list_app.data.model.Note
import com.pawatr.todo_list_app.databinding.ActivityMainBinding
import com.pawatr.todo_list_app.presentation.adapter.TodoAdapter
import com.pawatr.todo_list_app.presentation.viewmodel.NoteViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private var todoAdapter: TodoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        observeTodos()
        setupListeners()
    }

    private fun setupView() = with(binding) {
        todoAdapter = TodoAdapter(
            onCheckedChange = { todo, isChecked ->
                updateTodoChecked(todo, isChecked)
            },
            onItemClick = { todo ->
                displayEditTodoDialog(todo)
            },
            onDeleteClick = { todo ->
                noteViewModel.deleteTodo(todo)
            }
        )
        todoRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
    }

    private fun observeTodos() {
        lifecycleScope.launch {
            noteViewModel.todos.collect { todos ->
                todoAdapter?.todos = todos
            }
        }
    }

    private fun setupListeners() = with(binding) {
        fab.setOnClickListener {
            displayAddTodoDialog()
        }
    }

    private fun updateTodoChecked(todo: Note, isChecked: Boolean) {
        val newTodo = todo.copy(isCompleted = isChecked)
        noteViewModel.updateTodo(newTodo)
    }

    private fun displayAddTodoDialog() {
        TodoDialogFragment { newTodo ->
            noteViewModel.insertTodo(newTodo)
        }.show(supportFragmentManager, "AddTodoDialog")
    }

    private fun displayEditTodoDialog(todo: Note) {
        TodoDialogFragment(todo) { updatedTodo ->
            noteViewModel.updateTodo(updatedTodo)
        }.show(supportFragmentManager, "EditTodoDialog")
    }
}