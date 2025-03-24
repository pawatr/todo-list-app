package com.pawatr.todo_list_app.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pawatr.todo_list_app.data.model.Note
import com.pawatr.todo_list_app.databinding.ItemTodoBinding

@SuppressLint("NotifyDataSetChanged")
class TodoAdapter(
    val onCheckedChange: (Note, Boolean) -> Unit,
    val onItemClick: (Note) -> Unit,
    val onDeleteClick: (Note) -> Unit
) : RecyclerView.Adapter<TodoAdapter.ToDoViewHolder>() {

    var todos: List<Note> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    inner class ToDoViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                checkbox.isChecked = note.isCompleted
                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    onCheckedChange(note, isChecked)
                }
                title.text = note.title
                description.text = note.description
                deleteButton.setOnClickListener {
                    onDeleteClick(note)
                }
                root.setOnClickListener {
                    onItemClick(note)
                }
            }
        }
    }
}