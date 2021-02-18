package com.ikmr.banbara23.yaeyama_liner_checker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.ikmr.banbara23.yaeyama_liner_checker.model.Typhoon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

/**
 * 台風情報を取得
 */
@ExperimentalCoroutinesApi
class TyphoonRepository(private val dbRef: DatabaseReference) {

    // https://imstudio.medium.com/android-firebase-kotlin-part-3-firebase-realtime-database-f394c9c9d58a
    private val genericTypeIndicator = object : GenericTypeIndicator<List<Typhoon>>() {}

    fun fetchTyphoonList(): Flow<TyphoonUiState> = callbackFlow {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.getValue(genericTypeIndicator)?.let { typhoon ->
                    Timber.d(typhoon.toString())
                    offer(TyphoonUiState.Success(typhoon))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                offer(TyphoonUiState.Error(error.message))
            }
        })
    }
}

sealed class TyphoonUiState {
    data class Success(val typhoonList: List<Typhoon>) : TyphoonUiState()
    data class Error(val message: String) : TyphoonUiState()
}
