package com.yaeyama.linerchecker.repository

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
import com.yaeyama.linerchecker.domain.statusdetail.Company
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.statusdetail.Status
import com.yaeyama.linerchecker.domain.timetable.Header
import com.yaeyama.linerchecker.domain.timetable.TimeTable
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class StatusDetailRepositoryImplTest {

    private val database = mockk<FirebaseDatabase>()
    private val reference = mockk<DatabaseReference>(relaxed = true)
    private val listenerSlot = slot<ValueEventListener>()
    private val repository = StatusDetailRepositoryImpl(database)

    init {
        every { database.getReference(any()) } returns reference
        every { reference.addValueEventListener(capture(listenerSlot)) } answers { listenerSlot.captured }
    }

    @Test
    fun `fetchStatusDetail subscribes to the company and port path`() = runTest {
        repository.fetchStatusDetail(Company.ANEI, "taketomi").test {
            cancelAndIgnoreRemainingEvents()
        }

        verify { database.getReference("anei/taketomi") }
    }

    @Test
    fun `fetchStatusDetail emits the deserialized port status`() = runTest {
        val portStatus = PortStatus(
            portCode = "taketomi",
            portName = "竹富航路",
            status = Status(code = "normal", text = "通常運航"),
        )
        val snapshot = snapshotOf(portStatus)

        repository.fetchStatusDetail(Company.ANEI, "taketomi").test {
            listenerSlot.captured.onDataChange(snapshot)
            assertEquals(portStatus, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchStatusDetail emits every update while subscribed`() = runTest {
        val first = PortStatus(status = Status(code = "normal"))
        val second = PortStatus(status = Status(code = "cancel"))

        repository.fetchStatusDetail(Company.YKF, "kohama").test {
            listenerSlot.captured.onDataChange(snapshotOf(first))
            assertEquals(first, awaitItem())
            listenerSlot.captured.onDataChange(snapshotOf(second))
            assertEquals(second, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchStatusDetail fails with DataNotFoundException when no data exists`() = runTest {
        repository.fetchStatusDetail(Company.YKF, "hateruma").test {
            listenerSlot.captured.onDataChange(snapshotOf<PortStatus>(null))
            val error = awaitError()
            assertTrue(error is DataNotFoundException)
            assertEquals("ykf/hateruma", (error as DataNotFoundException).path)
        }
    }

    @Test
    fun `fetchStatusDetail fails with DataParseException when deserialization fails`() = runTest {
        val snapshot = mockk<DataSnapshot>()
        every { snapshot.getValue(any<GenericTypeIndicator<PortStatus>>()) } throws DatabaseException("Failed to convert value")

        repository.fetchStatusDetail(Company.ANEI, "taketomi").test {
            listenerSlot.captured.onDataChange(snapshot)
            assertTrue(awaitError() is DataParseException)
        }
    }

    @Test
    fun `fetchStatusDetail fails with DataFetchException when cancelled`() = runTest {
        repository.fetchStatusDetail(Company.ANEI, "taketomi").test {
            listenerSlot.captured.onCancelled(DatabaseError.fromCode(DatabaseError.PERMISSION_DENIED))
            assertTrue(awaitError() is DataFetchException)
        }
    }

    @Test
    fun `fetchStatusDetail removes the listener when collection is cancelled`() = runTest {
        repository.fetchStatusDetail(Company.ANEI, "taketomi").test {
            cancelAndIgnoreRemainingEvents()
        }

        verify { reference.removeEventListener(listenerSlot.captured) }
    }

    @Test
    fun `fetchTimeTable subscribes to the company time table path and emits the time table`() = runTest {
        val timeTable = TimeTable(header = Header(left = "石垣島", right = "竹富島"))

        repository.fetchTimeTable(Company.YKF, "taketomi").test {
            listenerSlot.captured.onDataChange(snapshotOf(timeTable))
            assertEquals(timeTable, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        verify { database.getReference("ykf_timeTable/taketomi") }
    }

    @Test
    fun `fetchTimeTable fails with DataNotFoundException when no data exists`() = runTest {
        repository.fetchTimeTable(Company.ANEI, "hateruma").test {
            listenerSlot.captured.onDataChange(snapshotOf<TimeTable>(null))
            val error = awaitError()
            assertTrue(error is DataNotFoundException)
            assertEquals("anei_timeTable/hateruma", (error as DataNotFoundException).path)
        }
    }

    private inline fun <reified T : Any> snapshotOf(value: T?): DataSnapshot {
        val snapshot = mockk<DataSnapshot>()
        every { snapshot.getValue(any<GenericTypeIndicator<T>>()) } returns value
        return snapshot
    }
}
