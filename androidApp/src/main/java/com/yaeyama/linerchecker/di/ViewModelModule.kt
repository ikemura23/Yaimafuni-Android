package com.yaeyama.linerchecker.di

import com.yaeyama.linerchecker.ui.dashboard.DashBoardViewModel
import com.yaeyama.linerchecker.ui.main.MainViewModel
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailViewModel
import com.yaeyama.linerchecker.ui.typhoon.list.TyphoonListViewModel
import com.yaeyama.linerchecker.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * https://insert-koin.io/docs/reference/koin-android/viewmodel/
 */
val viewModelModule = module {
    single {
        MainViewModel(get())
    }
    single {
        WeatherViewModel()
    }
    viewModel {
        PortStatusDetailViewModel(get())
    }
    viewModel {
        DashBoardViewModel(get())
    }
    single {
        TyphoonListViewModel()
    }
}
