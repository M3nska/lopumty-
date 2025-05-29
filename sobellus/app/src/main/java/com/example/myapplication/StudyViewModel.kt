package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.StudyNote
import com.example.myapplication.data.StudyNoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyViewModel(private val dao: StudyNoteDao) : ViewModel() {

    // LiveData from the database
    val studyNotes: LiveData<List<StudyNote>> = dao.getAllNotes()

    // Insert a new note asynchronously
    fun addNote(note: StudyNote) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(note)
        }
    }

    // Delete a note asynchronously
    fun deleteNote(note: StudyNote) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(note)
        }
    }

    // Clear all notes asynchronously
    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAll()
        }
    }
    //delete all function
    fun deleteAllNotes() {
        viewModelScope.launch {
            dao.deleteAll()
        }
    }
}
