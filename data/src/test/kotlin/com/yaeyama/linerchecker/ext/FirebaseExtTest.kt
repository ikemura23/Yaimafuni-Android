package com.yaeyama.linerchecker.ext

import app.cash.turbine.test
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.yaeyama.linerchecker.domain.common.DataFetchException
import com.yaeyama.linerchecker.domain.common.DataNotFoundException
import com.yaeyama.linerchecker.domain.common.DataParseException
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FirebaseExtTest {

    private val database = mockk<FirebaseDatabase>()
    private val reference = mockk<DatabaseReference>(relaxed = true)
    private val listenerSlot = slot<ValueEventListener>()

    init {
        every { database.getReference(PATH) } returns reference
        every { reference.addValueEventListener(capture(listenerSlot)) } answers { listenerSlot.captured }
    }

    @Test
    fun `valueEvents emits snapshot on data change and removes listener on close`() = runTest {
        val snapshot = mockk<DataSnapshot>()

        database.valueEvents(PATH).test {
            listenerSlot.captured.onDataChange(snapshot)
            assertEquals(snapshot, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        verify { reference.removeEventListener(listenerSlot.captured) }
    }

    @Test
    fun `valueEvents closes with DataFetchException on cancelled`() = runTest {
        val error = mockk<DatabaseError>()
        val cause = DatabaseException("boom")
        every { error.toException() } returns cause

        database.valueEvents(PATH).test {
            listenerSlot.captured.onCancelled(error)
            val thrown = awaitError()
            assertTrue(thrown is DataFetchException)
            assertEquals(PATH, (thrown as DataFetchException).path)
            assertEquals(cause, thrown.cause)
        }
    }

    @Test
    fun `valueEvents with converter emits converted value`() = runTest {
        database.valueEvents(PATH) { "converted" }.test {
            listenerSlot.captured.onDataChange(mockk())
            assertEquals("converted", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `valueEvents with converter closes with DataNotFoundException when converter returns null`() = runTest {
        database.valueEvents<String>(PATH) { null }.test {
            listenerSlot.captured.onDataChange(mockk())
            val thrown = awaitError()
            assertTrue(thrown is DataNotFoundException)
            assertEquals(PATH, (thrown as DataNotFoundException).path)
        }
    }

    @Test
    fun `valueEvents with converter closes with DataParseException when deserialization fails`() = runTest {
        val cause = DatabaseException("Failed to convert value")

        database.valueEvents<String>(PATH) { throw cause }.test {
            listenerSlot.captured.onDataChange(mockk())
            val thrown = awaitError()
            assertTrue(thrown is DataParseException)
            assertEquals(PATH, (thrown as DataParseException).path)
            assertEquals(cause, thrown.cause)
        }
    }

    @Test
    fun `valueEventsOf deserializes snapshot into the requested type`() = runTest {
        val snapshot = mockk<DataSnapshot>()
        val portStatus = PortStatus(portName = "竹富")
        every { snapshot.getValue(any<GenericTypeIndicator<PortStatus>>()) } returns portStatus

        database.valueEventsOf<PortStatus>(PATH).test {
            listenerSlot.captured.onDataChange(snapshot)
            assertEquals(portStatus, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private companion object {
        const val PATH = "path/to/data"
    }
}
