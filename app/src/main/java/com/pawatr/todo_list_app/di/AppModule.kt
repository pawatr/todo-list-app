package com.pawatr.todo_list_app.di

import androidx.room.Room
import com.pawatr.todo_list_app.data.local.NoteDatabase
import com.pawatr.todo_list_app.domain.repositories.NoteRepository
import com.pawatr.todo_list_app.domain.repositories.NoteRepositoryImpl
import com.pawatr.todo_list_app.domain.usecase.DeleteNoteUseCase
import com.pawatr.todo_list_app.domain.usecase.GetAllNotesUseCase
import com.pawatr.todo_list_app.domain.usecase.InsertNoteUseCase
import com.pawatr.todo_list_app.domain.usecase.UpdateNoteUseCase
import com.pawatr.todo_list_app.presentation.viewmodel.NoteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Database
    single { Room.databaseBuilder(androidContext(), NoteDatabase::class.java, "note_database").build() }
    single { get<NoteDatabase>().noteDao() }
    // Repository
    single<NoteRepository> { NoteRepositoryImpl(get()) }
    // UseCases
    factory { GetAllNotesUseCase(get()) }
    factory { InsertNoteUseCase(get()) }
    factory { UpdateNoteUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }
    // ViewModel
    viewModel { NoteViewModel(get(), get(), get(), get()) }
}