package data.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.ikemura.shared.repository.TyphoonRepository
import com.ikemura.shared.repository.TyphoonRepositoryImpl
import com.ikemura.shared.repository.WeatherRepository
import com.ikemura.shared.repository.WeatherRepositoryImpl
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val coreModule = module {
    single<FirebaseDatabase> { Firebase.database }
    single<WeatherRepository> {
        WeatherRepositoryImpl()
    }
    single<TyphoonRepository> {
        TyphoonRepositoryImpl()
    }
}

fun initKoin(): KoinApplication = startKoin {
    modules(coreModule)
}
