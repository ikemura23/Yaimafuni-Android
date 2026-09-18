package com.yaeyama.linerchecker.ext

import app.cash.turbine.test
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FirebaseExtTest {

    @Test
    fun `valueEvents emits snapshot on data change and removes listener on close`() = runTest {
        val reference = mockk<DatabaseReference>(relaxed = true)
        val snapshot = mockk<DataSnapshot>()
        val listenerSlot = slot<ValueEventListener>()
        every { reference.addValueEventListener(capture(listenerSlot)) } answers { listenerSlot.captured }

        reference.valueEvents.test {
            listenerSlot.captured.onDataChange(snapshot)
            assertEquals(snapshot, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        verify { reference.removeEventListener(listenerSlot.captured) }
    }

    @Test
    fun `valueEvents closes with exception on cancelled`() = runTest {
        val reference = mockk<DatabaseReference>(relaxed = true)
        val listenerSlot = slot<ValueEventListener>()
        val error = mockk<DatabaseError>()
        val exception = DatabaseException("boom")
        every { error.toException() } returns exception
        every { reference.addValueEventListener(capture(listenerSlot)) } answers { listenerSlot.captured }

        reference.valueEvents.test {
            listenerSlot.captured.onCancelled(error)
            val thrown = awaitError()
            assertEquals(DatabaseException::class, thrown::class)
            assertEquals("boom", thrown.message)
        }
    }
}
