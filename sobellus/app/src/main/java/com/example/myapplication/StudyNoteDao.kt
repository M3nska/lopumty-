package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudyNoteDao {

    @Query("SELECT * FROM study_notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<StudyNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: StudyNote): Long

    @Delete
    suspend fun delete(note: StudyNote): Int

    @Query("DELETE FROM study_notes")
    suspend fun deleteAll(): Int
}