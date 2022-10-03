package com.example.roomdbwithpaging.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.roomdbwithpaging.data.database.ContactDao
import com.example.roomdbwithpaging.data.model.ContactItem
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contactDao: ContactDao) {

    suspend fun insertContactList(contactList: List<ContactItem>) =
        contactDao.insertContactList(contactList)

    fun getContactFilteredList(query: String? = ""): LiveData<PagingData<ContactItem>> {
        return if (query.isNullOrEmpty()) {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20,
                    enablePlaceholders = false,
                    maxSize = 120
                ),
                pagingSourceFactory = { contactDao.getContacts() }
            ).liveData
        } else {
            Pager(
                PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false,
                    initialLoadSize = 20,
                    maxSize = 120
                ),
            ) {
                contactDao.getFilteredContacts(query)
            }.liveData
        }
    }
}