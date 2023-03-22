package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.customcalendarapplication.EventModel

@Dao
interface EventsDao {

    @Insert
    suspend fun insertEvent(evenModel: EventModel)

    @Query("select * from tbl_event")
    fun getAllData() : LiveData<List<EventModel>>

    @Query("select * from tbl_event where date = :date")
    fun getDataByDate(date : Long) : LiveData<List<EventModel>>
}