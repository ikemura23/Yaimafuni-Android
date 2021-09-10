package com.ikmr.banbara23.yaeyama_liner_checker.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.ikemura.shared.model.tyhoon.Typhoon
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

/**
 * 台風情報を取得
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class TyphoonRepositoryOld(private val dbRef: DatabaseReference) {

    fun getTyphoonList(): Flow<TyphoonUiState> = callbackFlow {
        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Timber.d("snapshot.childrenCount: ${snapshot.childrenCount}")
                // 0件チェック
                if (!snapshot.hasChildren()) {
                    trySend(TyphoonUiState.Success(listOf()))
                    return
                }
                snapshot.getValue<List<Typhoon>>()?.let { typhoon ->
                    Timber.d(typhoon.toString())
                    trySend(TyphoonUiState.Success(typhoon))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(TyphoonUiState.Error(error.message))
            }
        })
        awaitClose {
            // Firebase Databaseが自動で開放されるので何もしない
        }
    }
}

sealed class TyphoonUiState {
    data class Success(val typhoonList: List<Typhoon>) : TyphoonUiState()
    data class Error(val message: String) : TyphoonUiState()
}
