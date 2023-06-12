package com.yaeyama.linerchecker.di

import com.yaeyama.linerchecker.ui.main.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single {
        MainViewModel(get())
    }
}
