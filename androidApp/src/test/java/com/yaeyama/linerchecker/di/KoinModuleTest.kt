package com.yaeyama.linerchecker.di

import android.content.Context
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.verify.verify

/**
 * Koin の依存グラフを静的に検証する
 * 各定義のコンストラクタ引数がすべて登録されているか（登録漏れ・型の不一致が無いか）を確認する
 */
@OptIn(KoinExperimentalAPI::class)
class KoinModuleTest {

    @Test
    fun `all modules resolve their dependencies`() {
        module {
            includes(appModule, dataModule, useCaseModule, viewModelModule)
        }.verify(
            // androidContext() で登録される型
            extraTypes = listOf(Context::class),
        )
    }
}
