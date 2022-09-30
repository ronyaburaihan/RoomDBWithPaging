package com.example.roomdbwithpaging.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdbwithpaging.data.model.ContactItem

@Database(
    entities = [ContactItem::class],
    exportSchema = false,
    version = 1
)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}