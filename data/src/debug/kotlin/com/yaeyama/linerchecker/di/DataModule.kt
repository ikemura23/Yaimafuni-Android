package com.yaeyama.linerchecker.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.yaeyama.linerchecker.repository.FakeStatusDetailRepository
import com.yaeyama.linerchecker.repository.FakeTopStatusRepository
import com.yaeyama.linerchecker.repository.FakeTyphoonRepositoryImpl
import com.yaeyama.linerchecker.repository.StatusDetailRepository
import com.yaeyama.linerchecker.repository.TopStatusRepository
import com.yaeyama.linerchecker.repository.TyphoonRepository
import com.yaeyama.linerchecker.repository.WeatherRepository
import com.yaeyama.linerchecker.repository.WeatherRepositoryImpl
import org.koin.dsl.module

/**
 * DebugのRepositoryモジュール
 * Fake Repositoryを返すようにする
 */
val dataModule = module {
    single<FirebaseDatabase> { Firebase.database(Firebase.app) }
    single<WeatherRepository> {
        WeatherRepositoryImpl()
    }
    single<TyphoonRepository> {
        FakeTyphoonRepositoryImpl()
    }
    single<TopStatusRepository> { FakeTopStatusRepository() }
    single<StatusDetailRepository> { FakeStatusDetailRepository() }
}
