package data.di


import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val dataModule = module {
    // single<FirebaseDatabase> { Firebase.database(Firebase.app) }
}

fun initKoin(): KoinApplication = startKoin {
    modules(dataModule)
}

