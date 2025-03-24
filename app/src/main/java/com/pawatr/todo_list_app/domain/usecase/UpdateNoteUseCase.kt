package com.pawatr.todo_list_app.domain.usecase

import com.pawatr.todo_list_app.data.model.Note
import com.pawatr.todo_list_app.domain.repositories.NoteRepository

class UpdateNoteUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        noteRepository.updateNote(note)
    }
}