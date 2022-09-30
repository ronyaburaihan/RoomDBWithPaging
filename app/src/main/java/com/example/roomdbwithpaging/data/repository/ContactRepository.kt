package com.example.roomdbwithpaging.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.roomdbwithpaging.data.database.ContactDao
import com.example.roomdbwithpaging.data.model.ContactItem
import com.example.roomdbwithpaging.paging.ContactPagingSource
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contactDao: ContactDao) {

    suspend fun insertContactList(contactList: List<ContactItem>) =
        contactDao.insertContactList(contactList)

    fun getContacts() = Pager(
        config = PagingConfig(
            pageSize = 50,
            initialLoadSize = 50,
            enablePlaceholders = false,
            maxSize = 150
        ),
        pagingSourceFactory = { ContactPagingSource(contactDao) }
    ).liveData

    fun getContactList() = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20,
            maxSize = 120
        ),
    ) {
        ContactPagingSource(contactDao)
    }.liveData
}