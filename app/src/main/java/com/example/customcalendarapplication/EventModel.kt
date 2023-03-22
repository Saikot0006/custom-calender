package com.example.customcalendarapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_event")
data class EventModel(
    @PrimaryKey(autoGenerate = true)
    val id :  Long = 0,
    val name : String,
    val date : Long,
)
