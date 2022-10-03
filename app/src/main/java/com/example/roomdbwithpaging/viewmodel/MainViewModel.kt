package com.example.roomdbwithpaging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.roomdbwithpaging.data.model.ContactItem
import com.example.roomdbwithpaging.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ContactRepository) : ViewModel() {

    val contactList = repository.getContactFilteredList().cachedIn(viewModelScope)

    fun getContactList(query: String): LiveData<PagingData<ContactItem>> {
        return repository.getContactFilteredList(query).cachedIn(viewModelScope)
    }

    fun insertContactList(contactList: List<ContactItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertContactList(contactList)
        }
    }
}