package com.yaeyama.linerchecker.ui.common

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

/**
 * Repository / UseCase から読み込むデータの UI 状態
 */
sealed interface LoadState<out T> {
    data object Loading : LoadState<Nothing>

    data class Success<out T>(val data: T) : LoadState<T>

    data class Error(@StringRes val messageRes: Int) : LoadState<Nothing>
}

/**
 * データの Flow を [LoadState] の Flow に変換する
 *
 * 購読開始時に [LoadState.Loading]、値ごとに [LoadState.Success]、
 * 失敗時は例外の種類に応じたメッセージの [LoadState.Error] を流す。
 */
fun <T> Flow<T>.asLoadState(
    @StringRes notFoundRes: Int,
    @StringRes fetchFailedRes: Int,
): Flow<LoadState<T>> =
    map<T, LoadState<T>> { LoadState.Success(it) }
        .onStart { emit(LoadState.Loading) }
        .catch { e ->
            Timber.e(e, "load failed")
            emit(LoadState.Error(e.toErrorMessageRes(notFoundRes = notFoundRes, fetchFailedRes = fetchFailedRes)))
        }

/** 画面が購読をやめてから upstream を止めるまでの猶予（画面回転などで再購読しないため） */
private const val STOP_TIMEOUT_MILLIS = 5_000L

/**
 * ViewModel で UI 状態を公開するための共通の stateIn
 * 画面が購読している間だけ upstream（Firebase のリスナー）を動かす
 */
fun <T> Flow<T>.stateInWhileSubscribed(viewModel: ViewModel, initialValue: T): StateFlow<T> =
    stateIn(
        scope = viewModel.viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
        initialValue = initialValue,
    )

/**
 * [trigger] が値を流すたびに [load] をやり直す Flow
 * 再試行の仕組みを ViewModel 間で揃えるために使う
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <K, T> Flow<K>.reloadOnEach(load: (K) -> Flow<T>): Flow<T> = flatMapLatest { load(it) }
