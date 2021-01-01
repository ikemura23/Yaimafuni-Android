package com.ikmr.banbara23.yaeyama_liner_checker.front.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ikmr.banbara23.yaeyama_liner_checker.core.LiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.core.SingleLiveEvent
import com.ikmr.banbara23.yaeyama_liner_checker.model.weather.WeatherInfo

class WeatherScreenViewModel : ViewModel() {
    val item = MutableLiveData<WeatherInfo>()

    private lateinit var database: DatabaseReference// ...
    var event = SingleLiveEvent<Nav>()

    /**
     * 天気取得
     */
    fun load() {
        database = FirebaseDatabase.getInstance().reference.ref
        database.child("weather").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("WeatherViewModel", p0.message)
                event.postValue(Nav.Error)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                item.value = snapshot.getValue(WeatherInfo::class.java)
                item.value?.let {
                    Log.d("WeatherViewModel", it.toString())
                }
            }
        })
    }

    /**
     * 天気を詳しく見るをクリック
     */
    fun moreButtonClick() {
        event.setValue(Nav.More)
    }

    sealed class Nav : LiveEvent {
        object Error : Nav()
        object More : Nav()
    }
}

sealed class WeatherUiState {
    data class Success(val weatherInfo: WeatherInfo)
    data class Error(val message: String)
}
