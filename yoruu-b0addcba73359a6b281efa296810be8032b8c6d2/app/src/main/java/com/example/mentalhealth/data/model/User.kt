package com.example.mentalhealth.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val profilePicture: String? = null,
    val createdAt: Long = System.currentTimeMillis()
) 