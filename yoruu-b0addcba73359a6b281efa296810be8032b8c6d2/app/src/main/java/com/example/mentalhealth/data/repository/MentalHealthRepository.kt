package com.example.mentalhealth.data.repository

import com.example.mentalhealth.data.dao.MoodEntryDao
import com.example.mentalhealth.data.dao.UserDao
import com.example.mentalhealth.data.model.MoodEntry
import com.example.mentalhealth.data.model.User
import kotlinx.coroutines.flow.Flow
import java.util.Date

class MentalHealthRepository(
    private val moodEntryDao: MoodEntryDao,
    private val userDao: UserDao
) {
    // User operations
    fun getUserById(userId: String): Flow<User?> = userDao.getUserById(userId)
    
    suspend fun saveUser(user: User) = userDao.insertUser(user)
    
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    // Mood entries operations
    fun getAllMoodEntries(): Flow<List<MoodEntry>> = moodEntryDao.getAllEntries()
    
    suspend fun addMoodEntry(entry: MoodEntry) = moodEntryDao.insertEntry(entry)
    
    suspend fun updateMoodEntry(entry: MoodEntry) = moodEntryDao.updateEntry(entry)
    
    suspend fun deleteMoodEntry(entry: MoodEntry) = moodEntryDao.deleteEntry(entry)
    
    fun getMoodEntriesBetweenDates(startDate: Date, endDate: Date): Flow<List<MoodEntry>> =
        moodEntryDao.getEntriesBetweenDates(startDate, endDate)
} 