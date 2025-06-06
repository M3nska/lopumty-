package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudyNote::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    //function for getting dao
    abstract fun studyNoteDao(): StudyNoteDao
    // code for checking if there is a database if not then create else skip
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "study_notes_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
