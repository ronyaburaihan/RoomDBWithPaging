package com.example.roomdbwithpaging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactItem(
    @PrimaryKey(autoGenerate = true)
    val _id: Int? = null,
    val name: String,
    val phoneNumber: String
)