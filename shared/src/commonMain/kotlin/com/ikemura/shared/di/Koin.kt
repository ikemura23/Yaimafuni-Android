package com.ikemura.shared.di

import com.ikemura.shared.repository.TyphoonRepository
import com.ikemura.shared.repository.TyphoonRepositoryImpl
import com.ikemura.shared.repository.WeatherRepository
import com.ikemura.shared.repository.WeatherRepositoryImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val coreModule = module {
    single<FirebaseDatabase> { Firebase.database(Firebase.app) }
    single<WeatherRepository> {
        WeatherRepositoryImpl()
    }
    single<TyphoonRepository> {
        TyphoonRepositoryImpl()
//        FakeTyphoonRepositoryImpl()
    }
}

fun initKoin(): KoinApplication = startKoin {
    modules(coreModule)
}
