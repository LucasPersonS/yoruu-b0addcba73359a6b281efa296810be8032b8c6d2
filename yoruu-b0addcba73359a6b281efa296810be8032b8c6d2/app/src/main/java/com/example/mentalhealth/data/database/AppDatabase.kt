package com.example.mentalhealth.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mentalhealth.data.dao.MoodEntryDao
import com.example.mentalhealth.data.dao.UserDao
import com.example.mentalhealth.data.model.MoodEntry
import com.example.mentalhealth.data.model.User

@Database(entities = [MoodEntry::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodEntryDao(): MoodEntryDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mental_health_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 