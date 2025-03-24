package com.pawatr.todo_list_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawatr.todo_list_app.data.model.Note
import com.pawatr.todo_list_app.domain.usecase.DeleteNoteUseCase
import com.pawatr.todo_list_app.domain.usecase.GetAllNotesUseCase
import com.pawatr.todo_list_app.domain.usecase.InsertNoteUseCase
import com.pawatr.todo_list_app.domain.usecase.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _todos = MutableStateFlow<List<Note>>(emptyList())
    val todos: StateFlow<List<Note>> = _todos

    init {
        viewModelScope.launch {
            getAllNotesUseCase().collect {
                _todos.value = it
            }
        }
    }

    fun insertTodo(note: Note) {
        viewModelScope.launch {
            insertNoteUseCase(note)
        }
    }

    fun updateTodo(note: Note) {
        viewModelScope.launch {
            updateNoteUseCase(note)
        }
    }

    fun deleteTodo(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }
}