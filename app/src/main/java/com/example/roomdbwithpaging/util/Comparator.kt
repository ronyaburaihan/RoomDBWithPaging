package com.example.roomdbwithpaging.util

import androidx.recyclerview.widget.DiffUtil
import com.example.roomdbwithpaging.data.model.ContactItem

class Comparator : DiffUtil.ItemCallback<ContactItem>() {
    override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem == newItem
    }
}