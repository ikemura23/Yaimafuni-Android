package com.yaeyama.linerchecker.di

import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.yaeyama.linerchecker.repository.StatusDetailRepositoryImpl
import com.yaeyama.linerchecker.repository.TopStatusRepositoryImpl
import com.yaeyama.linerchecker.repository.TyphoonRepositoryImpl
import com.yaeyama.linerchecker.repository.WeatherRepositoryImpl
import com.yaeyama_liner_checker.domain.repository.StatusDetailRepository
import com.yaeyama_liner_checker.domain.repository.TopStatusRepository
import com.yaeyama_liner_checker.domain.repository.TyphoonRepository
import com.yaeyama_liner_checker.domain.repository.WeatherRepository
import org.koin.dsl.module

/**
 * debug用のdata module（releaseと同じ実装を使用）
 */
val dataModule = module {
    single<FirebaseDatabase> { Firebase.database(Firebase.app) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single<TyphoonRepository> { TyphoonRepositoryImpl(get()) }
    single<TopStatusRepository> { TopStatusRepositoryImpl(get()) }
    single<StatusDetailRepository> { StatusDetailRepositoryImpl(get()) }
}
