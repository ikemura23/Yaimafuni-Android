package com.yaeyama.linerchecker.di

import com.yaeyama.linerchecker.domain.usecase.GetTopStatuses
import com.yaeyama.linerchecker.domain.usecase.GetTyphoonList
import com.yaeyama.linerchecker.domain.usecase.GetWeatherInfo
import org.koin.dsl.module

/**
 * domain の UseCase
 * domain モジュールは Koin に依存させないため、androidApp で登録する
 */
val useCaseModule = module {
    factory { GetTopStatuses(get()) }
    factory { GetTyphoonList(get()) }
    factory { GetWeatherInfo(get()) }
}
