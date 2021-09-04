package com.ikemura.shared

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val coreModule = module {
    single<FirebaseDatabase> { Firebase.database(Firebase.app) }
}

fun initKoin(): KoinApplication = startKoin {
    modules(coreModule)
}