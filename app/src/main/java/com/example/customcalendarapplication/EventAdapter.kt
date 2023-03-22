package com.example.customcalendarapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.customcalendarapplication.databinding.EventRowItemBinding

class EventAdapter : ListAdapter<EventModel, EventAdapter.CalendarViewHolder>(CalendarDiffUtils()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
         var binding = EventRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
         return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        var events = getItem(position)
        holder.bind(events)
    }


    class CalendarViewHolder(private var binding : EventRowItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(eventModel: EventModel){
            binding.event = eventModel
        }
    }

    class CalendarDiffUtils : DiffUtil.ItemCallback<EventModel>() {
        override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
            return oldItem == newItem
        }

    }


}