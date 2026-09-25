package com.yaeyama.linerchecker.testing

import com.yaeyama.linerchecker.domain.repository.StatusDetailRepository
import com.yaeyama.linerchecker.domain.repository.TopStatusRepository
import com.yaeyama.linerchecker.domain.repository.TyphoonRepository
import com.yaeyama.linerchecker.domain.repository.WeatherRepository
import io.mockk.mockk
import org.koin.dsl.module

/**
 * テスト用の Repository module
 * Firebase に接続せずに Koin の依存グラフを組み立てるため、Repository を MockK のモックに差し替える。
 * 挙動を指定したい場合は、テスト側で get<XxxRepository>() したモックに every { } を設定する。
 */
val testRepositoryModule = module {
    single<WeatherRepository> { mockk(relaxed = true) }
    single<TyphoonRepository> { mockk(relaxed = true) }
    single<TopStatusRepository> { mockk(relaxed = true) }
    single<StatusDetailRepository> { mockk(relaxed = true) }
}
