package data.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.ikemura.shared.repository.TyphoonRepository
import data.repository.TyphoonRepositoryImpl
import com.ikemura.shared.repository.WeatherRepository
import com.ikemura.shared.repository.WeatherRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<FirebaseDatabase> { Firebase.database(Firebase.app) }
    single<WeatherRepository> {
        WeatherRepositoryImpl()
    }
    single<TyphoonRepository> {
        TyphoonRepositoryImpl()
    }
}
