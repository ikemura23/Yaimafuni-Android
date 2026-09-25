package com.yaeyama.linerchecker.domain.usecase

import app.cash.turbine.test
import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.statusdetail.PortStatus
import com.yaeyama.linerchecker.domain.top.Ports
import com.yaeyama.linerchecker.domain.top.TopPort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTopStatusesTest {

    @Test
    fun `ports are listed in display order`() = runTest {
        val topPort = TopPort(
            hateruma = portsOf("hateruma"),
            hatoma = portsOf("hatoma"),
            kohama = portsOf("kohama"),
            taketomi = portsOf("taketomi"),
            kuroshima = portsOf("kuroshima"),
            oohara = portsOf("oohara"),
            uehara = portsOf("uehara"),
        )
        val useCase = GetTopStatuses(FakeTopStatusRepository(topPort))

        useCase().test {
            assertEquals(
                listOf("taketomi", "kohama", "kuroshima", "oohara", "uehara", "hatoma", "hateruma"),
                awaitItem().map { it.anei.portCode },
            )
            awaitComplete()
        }
    }

    private fun portsOf(portCode: String) = Ports(anei = PortStatus(portCode = portCode))

    private class FakeTopStatusRepository(private val topPort: TopPort) : TopStatusRepository {
        override fun fetchTopStatuses(): Flow<TopPort> = flowOf(topPort)
    }
}
