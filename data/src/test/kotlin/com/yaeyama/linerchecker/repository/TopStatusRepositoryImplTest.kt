package com.yaeyama.linerchecker.repository

import app.cash.turbine.test
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TopStatusRepositoryImplTest {

    @Test
    fun `fetchTopStatuses propagates the failure instead of swallowing it into an empty list`() = runTest {
        val database = mockk<FirebaseDatabase>()
        val reference = mockk<DatabaseReference>(relaxed = true)
        val listenerSlot = slot<ValueEventListener>()
        every { database.getReference("top_port") } returns reference
        every { reference.addValueEventListener(capture(listenerSlot)) } answers { listenerSlot.captured }

        val repository = TopStatusRepositoryImpl(database)

        repository.fetchTopStatuses().test {
            listenerSlot.captured.onCancelled(DatabaseError.fromCode(DatabaseError.PERMISSION_DENIED))
            awaitError()
        }
    }
}
