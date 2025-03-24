package com.pawatr.todo_list_app.domain.usecase

import com.pawatr.todo_list_app.data.model.Note
import com.pawatr.todo_list_app.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(private val noteRepository: NoteRepository) {
    operator fun invoke() : Flow<List<Note>> {
        return noteRepository.getAllNotes()
    }
}