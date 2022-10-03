package com.example.roomdbwithpaging.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbwithpaging.R
import com.example.roomdbwithpaging.adapter.ContactListAdapter
import com.example.roomdbwithpaging.data.model.ContactItem
import com.example.roomdbwithpaging.databinding.ActivityMainBinding
import com.example.roomdbwithpaging.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel
    private lateinit var contactListAdapter: ContactListAdapter

    private lateinit var contactList: ArrayList<ContactItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        contactListAdapter = ContactListAdapter()
        contactList = ArrayList()

        /*for (i in 1..100000) {
            val contactItem = ContactItem(name = "User$i", phoneNumber = "0123456789$i")
            contactList.add(contactItem)
        }

        mainViewModel.insertContactList(contactList)

        Log.d("MainActivity", "onCreate: ${contactList.size}")*/

        binding.tvHello.setOnClickListener {
            mainViewModel.insertContactList(contactList)
        }

        binding.rvContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            hasFixedSize()
            adapter = contactListAdapter
        }

        mainViewModel.contactList.observe(this) {
            contactListAdapter.submitData(lifecycle, it)
            Log.d("MainActivity", "ContactListSize: ${contactListAdapter.itemCount}")
        }

        CoroutineScope(Dispatchers.IO).launch {
            logData()
        }
    }

    private suspend fun logData() {
        Log.d("MainActivity", "ContactListSize: ${contactListAdapter.itemCount}")
        delay(1000)
        logData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val search = menu?.findItem(R.id.action_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = false
        searchView?.queryHint = "Type here..."
        searchView?.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        searchDatabase(query)
        return true
    }

    private fun searchDatabase(search: String?) {
        val query = if (search.isNullOrEmpty()) "" else "%$search%"
        mainViewModel.getContactList(query)
            .observe(this) {
                contactListAdapter.submitData(lifecycle, it)
            }
    }
}