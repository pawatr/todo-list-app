package com.pawatr.todo_list_app.domain.repositories

import com.pawatr.todo_list_app.data.local.NoteDao
import com.pawatr.todo_list_app.data.local.NoteEntity
import com.pawatr.todo_list_app.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteDao: NoteDao): NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { notes ->
            notes.map { it.toNote() }
        }
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toNoteEntity())
    }

    private fun Note.toNoteEntity(): NoteEntity {
        return NoteEntity(
            id = id,
            title = title,
            description = description,
            timeHour = timeHour,
            timeMinute = timeMinute,
            isCompleted = isCompleted
        )
    }
}