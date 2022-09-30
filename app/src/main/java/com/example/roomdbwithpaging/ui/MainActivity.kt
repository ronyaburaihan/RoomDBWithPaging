package com.example.roomdbwithpaging.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbwithpaging.adapter.ContactListAdapter
import com.example.roomdbwithpaging.data.model.ContactItem
import com.example.roomdbwithpaging.databinding.ActivityMainBinding
import com.example.roomdbwithpaging.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
        }*/

        Log.d("MainActivity", "onCreate: ${contactList.size}")

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
    }

}