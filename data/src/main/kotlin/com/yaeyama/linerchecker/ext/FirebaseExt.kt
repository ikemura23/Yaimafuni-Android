package com.yaeyama.linerchecker.ext

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yaeyama.linerchecker.domain.common.DataFetchException
import com.yaeyama.linerchecker.domain.common.DataNotFoundException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

/**
 * [path] の値の変更を購読する Flow
 * 取得がキャンセルされた場合は [DataFetchException] で終了する
 */
fun FirebaseDatabase.valueEvents(path: String): Flow<DataSnapshot> = callbackFlow {
    val reference = getReference(path)
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            trySend(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {
            close(DataFetchException(path, error.toException()))
        }
    }
    reference.addValueEventListener(listener)
    awaitClose {
        reference.removeEventListener(listener)
    }
}

/**
 * [path] の値の変更を [T] に変換して購読する Flow
 * [convert] が null を返した（データが存在しない）場合は [DataNotFoundException] で終了する
 */
inline fun <T : Any> FirebaseDatabase.valueEvents(
    path: String,
    crossinline convert: (DataSnapshot) -> T?,
): Flow<T> = valueEvents(path).map { snapshot ->
    convert(snapshot) ?: throw DataNotFoundException(path)
}
