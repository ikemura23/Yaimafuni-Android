package com.ikmr.banbara23.yaeyama_liner_checker.front.weather

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ikmr.banbara23.yaeyama_liner_checker.core.LiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo

class WeatherScreenViewModel : ViewModel() {
    val item = MutableLiveData<WeatherInfo>()

    private lateinit var database: DatabaseReference// ...

    fun load() {

        database = FirebaseDatabase.getInstance().reference.ref
        database.child("weather").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("WeatherViewModel", p0.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                item.value = snapshot.getValue(WeatherInfo::class.java)
                Log.d("WeatherViewModel", item.value.toString())
            }
        })
    }

    sealed class Nav : LiveEvent {
        data class Data(val data: WeatherInfo) : Nav()
    }
}

