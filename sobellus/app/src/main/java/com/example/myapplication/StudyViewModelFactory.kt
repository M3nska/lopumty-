package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.StudyNoteDao

class StudyViewModelFactory(
    private val dao: StudyNoteDao
) : ViewModelProvider.Factory {
    //Create viewmodel instance
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // check taht viewmodel is studyviewmodel
        if (modelClass.isAssignableFrom(StudyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudyViewModel(dao) as T
        }
        // error message if viewmodel that is not known is requested
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
