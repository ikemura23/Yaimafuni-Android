package com.yaeyama.linerchecker.domain.common

/**
 * Repository がデータ取得に失敗したことを表す例外の基底クラス
 *
 * Repository は取得結果を Flow で返し、失敗時はこの例外で Flow を終了する。
 *
 * @param path データを取得しようとしたパス
 */
sealed class DataException(
    val path: String,
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)

/**
 * 指定したパスにデータが存在しなかったことを表す例外
 */
class DataNotFoundException(
    path: String,
) : DataException(path, "No data found at path: $path")

/**
 * 通信エラーや権限エラーなどでデータを取得できなかったことを表す例外
 */
class DataFetchException(
    path: String,
    cause: Throwable,
) : DataException(path, "Failed to fetch data at path: $path", cause)

/**
 * 取得したデータを期待する型に変換できなかったことを表す例外
 */
class DataParseException(
    path: String,
    cause: Throwable,
) : DataException(path, "Failed to parse data at path: $path", cause)
