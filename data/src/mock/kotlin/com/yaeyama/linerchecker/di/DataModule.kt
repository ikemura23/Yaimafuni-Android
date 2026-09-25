package com.yaeyama.linerchecker.di

import com.yaeyama.linerchecker.domain.repository.StatusDetailRepository
import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.repository.TyphoonRepository
import com.yaeyama.linerchecker.domain.repository.WeatherRepository
import com.yaeyama.linerchecker.repository.FakeStatusDetailRepository
import com.yaeyama.linerchecker.repository.FakeTopStatusRepository
import com.yaeyama.linerchecker.repository.FakeTyphoonRepository
import com.yaeyama.linerchecker.repository.FakeWeatherRepository
import org.koin.dsl.module

/**
 * mock用のdata module
 */
val dataModule = module {
    single<WeatherRepository> {
        FakeWeatherRepository()
    }
    single<TyphoonRepository> {
        FakeTyphoonRepository()
    }
    single<TopStatusRepository> {
        FakeTopStatusRepository()
    }
    single<StatusDetailRepository> {
        FakeStatusDetailRepository()
    }
}
