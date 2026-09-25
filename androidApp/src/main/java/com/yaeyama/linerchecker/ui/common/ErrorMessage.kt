package com.yaeyama.linerchecker.ui.common

import androidx.annotation.StringRes
import com.yaeyama.linerchecker.domain.common.DataNotFoundException

/**
 * Repository から受け取った例外の種類に応じて、ユーザー向けのエラーメッセージを返す
 *
 * @param notFoundRes データが存在しなかったときのメッセージ
 * @param fetchFailedRes 通信エラーなどで取得に失敗したときのメッセージ
 */
@StringRes
fun Throwable.toErrorMessageRes(
    @StringRes notFoundRes: Int,
    @StringRes fetchFailedRes: Int,
): Int = when (this) {
    is DataNotFoundException -> notFoundRes
    else -> fetchFailedRes
}
