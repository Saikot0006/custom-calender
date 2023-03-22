package com.example.customcalendarapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.example.myapplication.EventDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var calendarView : CalendarView
    lateinit var buttonSave : Button
    lateinit var editText : EditText
    lateinit var textView : TextView
    var events: ArrayList<EventDay> = ArrayList()
    private var selectedDate : Long  = 0
    private lateinit var eventAdapter: EventAdapter
    private lateinit var calendarRV : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        buttonSave = findViewById(R.id.save)
        editText = findViewById(R.id.editText)
        calendarRV = findViewById(R.id.calendarRV)
        textView = findViewById(R.id.textView)

        eventAdapter = EventAdapter()

       // selectedDate = System.currentTimeMillis()
        val calendar =  Calendar.getInstance()
        selectedDate = calendar.timeInMillis
       // selectDateBackground(calendar)

        EventDB.getDB(applicationContext).getDao().getAllData().observe(this) {
            it.forEach {
                var calendar = Calendar.getInstance()
                calendar.timeInMillis = it.date
                events.add(EventDay(calendar,R.drawable.baseline_circle_24))
            }

            calendarView.setEvents(events)
        }
//
        calendarRV.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = eventAdapter
        }

        buttonSave.setOnClickListener {
            var eventName = editText.text.toString()
            var evenModel = EventModel(name = eventName, date = selectedDate)

            CoroutineScope(Dispatchers.Main).launch {
                EventDB.getDB(application).getDao().insertEvent(evenModel)
                editText.setText("")
            }


        }

        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                selectedDate = eventDay.calendar.timeInMillis
                val cal = Calendar.getInstance()
                cal.timeInMillis = selectedDate

                selectDateBackground(cal)

            }
        })


    }

    private fun selectDateBackground(cal: Calendar) {
        val list = listOf(
            CalendarDay(cal).apply {
                labelColor = R.color.black
                backgroundResource = R.drawable.simple_circle
            }
        )

        calendarView.setCalendarDays(list)
        eventNameList(cal.timeInMillis)
    }

    private fun eventNameList(cal : Long) {
        EventDB.getDB(applicationContext).getDao().getDataByDate(cal).observe(this@MainActivity) {
            if(it.size > 0){
                Log.e("AllEvent", "onDayClick: "+it.get(0).name)
                eventAdapter.submitList(it)
                //Log.e("event", "onDayClick: No Event" )
                calendarRV.visibility = View.VISIBLE
                textView.visibility = View.GONE

            }else{
                calendarRV.visibility = View.GONE
                textView.visibility = View.VISIBLE
                Log.e("event", "onDayClick: No Event" )
            }

            // eventList = it
            Log.e("selectedDate", "onDayClick: "+it.size.toString() )
        }
    }
}