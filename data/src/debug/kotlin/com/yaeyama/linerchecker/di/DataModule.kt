package com.yaeyama.linerchecker.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
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
 * DebugのRepositoryモジュール
 * Fake Repositoryを返すようにする
 */
val dataModule = module {
    single<FirebaseDatabase> { Firebase.database(Firebase.app) }
    single<WeatherRepository> {
        WeatherRepositoryImpl()
    }
    single<TyphoonRepository> {
        TyphoonRepositoryImpl()
    }
    single<TopStatusRepository> {
        TopStatusRepositoryImpl()
    }
    single<StatusDetailRepository> {
        StatusDetailRepositoryImpl()
    }
}
