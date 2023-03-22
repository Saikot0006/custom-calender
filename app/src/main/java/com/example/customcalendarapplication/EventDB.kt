package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.customcalendarapplication.EventModel

@Database(entities = [EventModel::class], version = 1)
abstract class EventDB : RoomDatabase(){
    abstract fun getDao() : EventsDao

    companion object{
        var db : EventDB? = null
        fun getDB(context: Context) : EventDB{
            if(db==null){
                db = Room.databaseBuilder(
                    context,
                    EventDB::class.java,
                    "calender_events_db"
                ).build()
            }

            return db!!
        }

    }


}