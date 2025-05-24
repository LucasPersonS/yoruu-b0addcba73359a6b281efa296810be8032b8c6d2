package com.example.mentalhealth.data.dao

import androidx.room.*
import com.example.mentalhealth.data.model.MoodEntry
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface MoodEntryDao {
    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<MoodEntry>>

    @Query("SELECT * FROM mood_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): MoodEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: MoodEntry): Long

    @Update
    suspend fun updateEntry(entry: MoodEntry)

    @Delete
    suspend fun deleteEntry(entry: MoodEntry)

    @Query("SELECT * FROM mood_entries WHERE timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getEntriesBetweenDates(startDate: Date, endDate: Date): Flow<List<MoodEntry>>
} 