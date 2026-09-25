package com.yaeyama.linerchecker.di

import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
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
 * release用のdata module
 */
val dataModule = module {
    single<FirebaseDatabase> { Firebase.database(Firebase.app) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single<TyphoonRepository> { TyphoonRepositoryImpl(get()) }
    single<TopStatusRepository> { TopStatusRepositoryImpl(get()) }
    single<StatusDetailRepository> { StatusDetailRepositoryImpl(get()) }
}
