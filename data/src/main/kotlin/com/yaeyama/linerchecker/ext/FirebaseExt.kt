package com.yaeyama.linerchecker.ext

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun FirebaseDatabase.reference(path: String): DatabaseReference {
    return this.getReference(path)
}

val DatabaseReference.valueEvents: Flow<DataSnapshot>
    get() = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        this@valueEvents.addValueEventListener(listener)
        awaitClose {
            this@valueEvents.removeEventListener(listener)
        }
    }

// data class DataSnapshot(
//     val snapshot: DataSnapshot,
// ) {
//     inline fun <reified T> value() = snapshot.getValue<T>()
//
//     val exists get() = snapshot.exists()
// }
