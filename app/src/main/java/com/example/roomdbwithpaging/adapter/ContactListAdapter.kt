package com.example.roomdbwithpaging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbwithpaging.data.model.ContactItem
import com.example.roomdbwithpaging.databinding.LayoutContactItemBinding
import com.example.roomdbwithpaging.util.Comparator

class ContactListAdapter :
    PagingDataAdapter<ContactItem, ContactListAdapter.ContactViewHolder>(Comparator()) {

    inner class ContactViewHolder(val binding: LayoutContactItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            with(holder.binding) {
                tvName.text = it.name
                tvPhoneNumber.text = it.phoneNumber
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutContactItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}