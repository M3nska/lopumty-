package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
// code for drivin sql with dao
@Dao
interface StudyNoteDao {
    //sql for getting information out from the database
    @Query("SELECT * FROM study_notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<StudyNote>>

    //sql for adding new lines into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: StudyNote): Long
    //sql for deleting a certai
    @Delete
    suspend fun delete(note: StudyNote): Int

    @Query("DELETE FROM study_notes")
    suspend fun deleteAll(): Int

}