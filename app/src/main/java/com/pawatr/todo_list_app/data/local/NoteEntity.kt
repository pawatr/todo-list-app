package com.pawatr.todo_list_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pawatr.todo_list_app.data.model.Note

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val timeHour: Int = 0,
    val timeMinute: Int = 0,
    val isCompleted: Boolean = false
) {
    fun toNote(): Note {
        return Note(
            id = id,
            title = title,
            description = description,
            timeHour = timeHour,
            timeMinute = timeMinute,
            isCompleted = isCompleted
        )
    }
}