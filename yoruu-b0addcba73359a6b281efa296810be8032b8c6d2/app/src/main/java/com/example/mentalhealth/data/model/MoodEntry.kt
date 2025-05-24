package com.example.mentalhealth.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mood: Int, // 1-5 scale
    val note: String,
    val timestamp: Date,
    val activities: List<String> = emptyList()
) 