package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
//structure of the dabase that is very simple cosinsting of subject and aciticity that are both string
@Entity(tableName = "study_notes")
data class StudyNote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val activity: String
)
