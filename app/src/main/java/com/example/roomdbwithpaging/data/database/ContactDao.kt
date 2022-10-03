package com.example.roomdbwithpaging.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdbwithpaging.data.model.ContactItem

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactList(contactList: List<ContactItem>)

    @Query("SELECT * FROM contacts ORDER BY _id ASC")
    fun getContacts(): PagingSource<Int, ContactItem>

    @Query("SELECT * FROM contacts WHERE name LIKE :query ORDER BY _id ASC")
    fun getFilteredContacts(query: String): PagingSource<Int, ContactItem>
}