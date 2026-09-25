package com.yaeyama.linerchecker.di

import com.google.firebase.database.FirebaseDatabase
import com.yaeyama.linerchecker.domain.repository.StatusDetailRepository
import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.repository.TyphoonRepository
import com.yaeyama.linerchecker.domain.repository.WeatherRepository
import com.yaeyama.linerchecker.repository.StatusDetailRepositoryImpl
import com.yaeyama.linerchecker.repository.TopStatusRepositoryImpl
import com.yaeyama.linerchecker.repository.TyphoonRepositoryImpl
import com.yaeyama.linerchecker.repository.WeatherRepositoryImpl
import org.koin.dsl.module

/**
 * debug用のdata module（releaseと同じ実装を使用）
 */
val dataModule = module {
    single<FirebaseDatabase> { createFirebaseDatabase() }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single<TyphoonRepository> { TyphoonRepositoryImpl(get()) }
    single<TopStatusRepository> { TopStatusRepositoryImpl(get()) }
    single<StatusDetailRepository> { StatusDetailRepositoryImpl(get()) }
}
