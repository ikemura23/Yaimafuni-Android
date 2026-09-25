package com.yaeyama_liner_checker.domain.common

/**
 * 指定したパスにデータが存在しなかったことを表す例外
 *
 * @param path データを取得しようとしたパス
 */
class DataNotFoundException(
    val path: String,
) : Exception("No data found at path: $path")
