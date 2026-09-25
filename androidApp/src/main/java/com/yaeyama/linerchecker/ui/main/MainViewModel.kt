package com.yaeyama.linerchecker.ui.main

import androidx.lifecycle.ViewModel
import com.yaeyama.linerchecker.domain.typhoon.Typhoon
import com.yaeyama.linerchecker.domain.usecase.GetTyphoonList
import com.yaeyama.linerchecker.ui.common.stateInWhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber

class MainViewModel(
    getTyphoonList: GetTyphoonList,
) : ViewModel() {

    val typhoonCount: StateFlow<Int> = getTyphoonList()
        .map { list: List<Typhoon> -> list.size }
        // バッジ表示のみのため、取得に失敗したらバッジを出さない（未捕捉の例外でクラッシュさせない）
        .catch { e ->
            Timber.e(e, "fetch typhoon count failed")
            emit(0)
        }
        .stateInWhileSubscribed(this, initialValue = 0)
}
