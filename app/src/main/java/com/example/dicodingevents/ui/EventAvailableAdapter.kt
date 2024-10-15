package com.example.dicodingevents.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevents.databinding.ItemEventAvailableBinding
import data.response.ListEventsItem

class EventAvailableAdapter(
    private val onItemClicked: (Int) -> Unit
) : ListAdapter<ListEventsItem, EventAvailableAdapter.EventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventAvailableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class EventViewHolder(
        private val binding: ItemEventAvailableBinding,
        private val onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.summary.text = event.name
            Glide.with(binding.image.context)
                .load(event.mediaCover)
                .into(binding.image)

            itemView.setOnClickListener {
                onItemClicked(event.id)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}