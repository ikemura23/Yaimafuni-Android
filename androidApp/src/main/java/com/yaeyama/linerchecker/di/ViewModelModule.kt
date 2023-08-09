package com.yaeyama.linerchecker.di

import com.yaeyama.linerchecker.ui.dashboard.DashBoardViewModel
import com.yaeyama.linerchecker.ui.main.MainViewModel
import com.yaeyama.linerchecker.ui.portstatusdetail.PortStatusDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * https://insert-koin.io/docs/reference/koin-android/viewmodel/
 */
val viewModelModule = module {
    single {
        MainViewModel(get())
    }
    viewModel {
        PortStatusDetailViewModel()
    }
    viewModel {
        DashBoardViewModel(get())
    }
}
