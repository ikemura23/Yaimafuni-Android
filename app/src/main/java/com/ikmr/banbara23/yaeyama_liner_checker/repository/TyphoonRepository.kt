package com.ikmr.banbara23.yaeyama_liner_checker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
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

    fun fetchTyphoonList(): Flow<TyphoonUiState> = callbackFlow {
        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // snapshot.getValue<List<@JvmSuppressWildcards Typhoon>>() <= firebase-database-ktxを使っている
                // https://imstudio.medium.com/android-firebase-kotlin-part-3-firebase-realtime-database-f394c9c9d58a
                snapshot.getValue<List<@JvmSuppressWildcards Typhoon>>()?.let { typhoon ->
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
